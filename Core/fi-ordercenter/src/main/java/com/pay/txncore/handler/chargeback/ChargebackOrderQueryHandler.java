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

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.ChargeBackOrder;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 拒付订单查询
 * 
 * @author chma
 */
public class ChargebackOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChargebackOrderQueryHandler.class);
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

		ChargeBackOrder chargeBackOrder = MapUtil.map2Object(
				ChargeBackOrder.class, paraMap);

		Map pageMap = (Map) paraMap.get("page");
		Page page = MapUtil.map2Object(Page.class, pageMap);

		List<ChargeBackOrder> chargeBackOrders = chargeBackOrderService
				.queryChargeBackOrders(chargeBackOrder, page);
		
		resultMap.put("result", chargeBackOrders);
		resultMap.put("page", page);

		return JSonUtil.toJSonString(resultMap);
	}

}
