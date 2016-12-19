/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.wechatAlipay;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证买家支付宝账号
 */
public class BuyerIdCheckRule extends MessageRule {
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

		String buyerId = wechatAlipayRequestDTO.getBuyerId();
		String tradeType = wechatAlipayRequestDTO.getTradeType();
		String language = wechatAlipayRequestDTO.getLanguage();
		
		//支付宝WAP支付时buyerId为必填
		if ((!TradeTypeEnum.WFT_ALIPAY_WAP.getCode().equals(tradeType)) || StringUtils.isNotEmpty(buyerId)) {
			return true;
		} else{
			if("cn".equals(language))
				wechatAlipayResponseDTO.setResultMsg(getMessage());
			else
				wechatAlipayResponseDTO.setResultMsg(getMessageEn());
			wechatAlipayResponseDTO.setResultCode(getMessageId());
			return false;
		}
		
	}

}
