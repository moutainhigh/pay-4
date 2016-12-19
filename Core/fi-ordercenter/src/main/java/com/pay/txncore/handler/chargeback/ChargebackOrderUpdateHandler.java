/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.chargeback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.commons.BouncedEnum;
import com.pay.txncore.model.ChargeBackOrder;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 拒付订单更新
 * 
 * @author chma
 */
public class ChargebackOrderUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChargebackOrderUpdateHandler.class);
	private ChargeBackOrderService chargeBackOrderService;

	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		List<Map> listMap = (List<Map>) paraMap.get("chargeBackOrders");
		List<ChargeBackOrder> chargeBackOrders = MapUtil.map2List(
				ChargeBackOrder.class, listMap);
		String flag=null;
		if(null!=paraMap.get("flag"))
		{
			flag=paraMap.get("flag").toString();
		}	
		for (ChargeBackOrder chargeBackOrder : chargeBackOrders) {

			try {
				//拒付的不申诉要扣款
				if (BouncedEnum.statusAudit7.getType().equals("" + chargeBackOrder.getStatus())
						&& ((BouncedEnum.bouncedType0.getType()).equals(""
								+ chargeBackOrder.getCpType()) || ((BouncedEnum.bouncedType1
								.getType()).equals("" + chargeBackOrder.getCpType()) && BouncedEnum.gcflag8
								.getType().equals(chargeBackOrder.getMerchantCode())))) {
					chargeBackOrder.setAmountType(BouncedEnum.amountType7.getType());
				}
				chargeBackOrderService.updateChargeBackOrder(chargeBackOrder);
				
				if("true".equals(flag))
				{	
				chargeBackOrderService.doChargeProcess(
						chargeBackOrder.getOrderId(),
						chargeBackOrder.getCpType(),
						chargeBackOrder.getStatus(),
						chargeBackOrder.getAuditOperator(),
						chargeBackOrder.getAuditMsg(),
						chargeBackOrder.getAppealDbRelativePath(),
						chargeBackOrder.getAmountType(),
						chargeBackOrder.getAccountingFlg()
						);
				}
				resultMap.put("responseCode",
						ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc",
						ResponseCodeEnum.SUCCESS.getDesc());
			} catch (BusinessException e) {
				logger.error("执行异常：", e);
				resultMap.put("responseCode", e.getCode().getCode());
				resultMap.put("responseDesc", e.getCode().getDescription());
			} catch (Exception e) {
				logger.error("执行异常：", e);
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc());
			}
		}

		return JSonUtil.toJSonString(resultMap);
	}

}
