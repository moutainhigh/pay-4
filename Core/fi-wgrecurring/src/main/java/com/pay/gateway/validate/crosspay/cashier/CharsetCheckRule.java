/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cashier;

import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.CharsetTypeEnum;

/**
 * 验证网关版本
 */
public class CharsetCheckRule extends MessageRule {
     private String messageEn;

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
		String language = onlineRequestDTO.getLanguage();

		String charset = onlineRequestDTO.getCharset();

		try{
			if (String.valueOf(CharsetTypeEnum.UTF8.getCode()).equals(charset)) {
				return true;
			} else {
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				
				onlineResponseDTO.setResultCode(getMessageId());
				return false;
			}	
		}catch(Exception e){
			if("cn".equals(language))
				onlineResponseDTO.setResultMsg(getMessage());
			else
				onlineResponseDTO.setResultMsg(getMessageEn());
			
			onlineResponseDTO.setResultCode(getMessageId());
			return false;
		}
	}

}
