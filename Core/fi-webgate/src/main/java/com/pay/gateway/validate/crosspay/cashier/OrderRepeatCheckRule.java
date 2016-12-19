/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cashier;

import java.util.HashMap;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证订单号重复
 */
public class OrderRepeatCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;
	private String messageEn;
	

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
	public String getMessageEn() {
		return messageEn;
	}
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayGatewayRequest onlineRequestDTO = (CrosspayGatewayRequest) validateBean;
		CrosspayGatewayResponse onlineResponseDTO = onlineRequestDTO
				.getGatewayResponseDTO();

		String orderId = onlineRequestDTO.getOrderId();
		String memberCode = onlineRequestDTO.getPartnerId();
		String language = onlineRequestDTO.getLanguage();
        
		Map<String,String> params = new HashMap<String, String>();
		params.put("orderId",orderId);
		params.put("partnerId",memberCode);
		params.put("status","1,2,3,4,20");
		
		Map map = txncoreClientService.completedOrderQuery(params); 
		
		String hasTradeOrder = (String) map.get("hasTradeOrder");
	
		
		if ("0".equals(hasTradeOrder)) {
			return true;
		} else {
			if("cn".equals(language))
				onlineResponseDTO.setResultMsg(getMessage());
			else
				onlineResponseDTO.setResultMsg(getMessageEn());
			onlineResponseDTO.setResultCode(getMessageId());
			return false;
		}
	}

}
