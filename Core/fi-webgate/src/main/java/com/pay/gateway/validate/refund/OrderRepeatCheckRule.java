/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证订单号重复
 */
public class OrderRepeatCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
		RefundApiResponse refundApiResponse = refundApiRequest
				.getRefundApiResponse();

		String orderId = refundApiRequest.getRefundOrderId();
		String memberCode = refundApiRequest.getPartnerId();
		String parnterRefundId = refundApiRequest.getRefundOrderId();
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("partnerId",memberCode);
		params.put("refundOrderId",parnterRefundId);
		
		Map map0 = txncoreClientService.refundOrderQuery(params);
		
		String hasRefundOrder = (String) map0.get("hasRefundOrder");
		
		if("1".equals(hasRefundOrder)){
			refundApiResponse.setResultCode(getMessageId());
			refundApiResponse.setResultMsg(getMessage());
			return false;
		}

		params = new HashMap<String, String>();
		params.put("partnerId",memberCode);
		params.put("orderId",orderId);
		params.put("status","3");
		
		Map map = txncoreClientService.completedOrderQuery(params);
		
		String hasTradeOrder = (String) map.get("hasTradeOrder");

		if ("0".equals(hasTradeOrder)) {
			return true;
		} else if("1".equals(hasTradeOrder)){
			Map<String,String> mapInfo = (Map<String, String>) map.get("mapInfo");
			String status = mapInfo.get("status");
			String refundAmount = mapInfo.get("refundAmount");
			
			BigDecimal ra = new BigDecimal("0");
			
			if(!StringUtil.isEmpty(refundAmount)){
				ra = new BigDecimal(refundAmount);
			}
			
			if("3".equals(status)
					&&ra.compareTo(new BigDecimal("0"))>0){
				return true;
			}
		}

		refundApiResponse.setResultCode(getMessageId());
		refundApiResponse.setResultMsg(getMessage());
		return false;
	}

}
