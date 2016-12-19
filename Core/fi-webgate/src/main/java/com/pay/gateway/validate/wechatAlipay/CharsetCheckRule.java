/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.wechatAlipay;

import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
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

		WechatAlipayRequest wechatAlipayRequestDTO = (WechatAlipayRequest) validateBean;
		WechatAlipayResponse wechatAlipayResponseDTO = wechatAlipayRequestDTO
				.getWechatAlipayResponseDTO();
		String language = wechatAlipayRequestDTO.getLanguage();

		String charset = wechatAlipayRequestDTO.getCharset();

		try{
			if (String.valueOf(CharsetTypeEnum.UTF8.getCode()).equals(charset)) {
				return true;
			} else {
				if("cn".equals(language))
					wechatAlipayResponseDTO.setResultMsg(getMessage());
				else
					wechatAlipayResponseDTO.setResultMsg(getMessageEn());
				
				wechatAlipayResponseDTO.setResultCode(getMessageId());
				return false;
			}	
		}catch(Exception e){
			if("cn".equals(language))
				wechatAlipayResponseDTO.setResultMsg(getMessage());
			else
				wechatAlipayResponseDTO.setResultMsg(getMessageEn());
			
			wechatAlipayResponseDTO.setResultCode(getMessageId());
			return false;
		}
	}

}
