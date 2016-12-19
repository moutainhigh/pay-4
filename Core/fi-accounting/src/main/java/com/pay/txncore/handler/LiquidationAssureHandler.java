/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 商户清算商户保证金
 * 
 * @author chma
 */
public class LiquidationAssureHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private int maxSize = 100;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		
		String assureSettlementFlgStr = (String) paraMap.get("assureSettlementFlg");
		
		int assureSettlementFlg=0;
		
		if(!StringUtil.isEmpty(assureSettlementFlgStr)){
			assureSettlementFlg = Integer.valueOf(assureSettlementFlgStr);
			if(assureSettlementFlg==1){
				resultMap.put("responseCode", ResponseCodeEnum.INVALID_PARAMETER.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.INVALID_PARAMETER.getDesc());
				return JSonUtil.toJSonString(resultMap);
			}
		}

		List<PartnerSettlementOrder> partnerSettlementOrders = partnerSettlementOrderService
				.queryUnSettlementAssurePartnerSettlementOrder(maxSize,assureSettlementFlg);
		if (null != partnerSettlementOrders
				&& !partnerSettlementOrders.isEmpty()) {
			for (PartnerSettlementOrder partnerSettlementOrder : partnerSettlementOrders) {
				try {
					paymentService.liquidationAssureRnTx(partnerSettlementOrder);
				} catch (Exception e) {
					logger.error("settlement error:", e);
				}
			}
		}

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

}
