/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.wechatAlipay;

import java.util.HashMap;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
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

		WechatAlipayRequest wechatAlipayRequestDTO = (WechatAlipayRequest) validateBean;
		WechatAlipayResponse wechatAlipayResponseDTO = wechatAlipayRequestDTO
				.getWechatAlipayResponseDTO();

		String orderId = wechatAlipayRequestDTO.getOrderId();
		String memberCode = wechatAlipayRequestDTO.getPartnerId();
		String language = wechatAlipayRequestDTO.getLanguage();
        
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
				wechatAlipayResponseDTO.setResultMsg(getMessage());
			else
				wechatAlipayResponseDTO.setResultMsg(getMessageEn());
			wechatAlipayResponseDTO.setResultCode(getMessageId());
			return false;
		}
	}

}
