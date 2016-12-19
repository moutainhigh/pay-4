/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.wechatAlipay;

import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关商户
 */
public class MerchantConfigureCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;
	private String messageEn;

	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
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

		try {
			String partnerId = wechatAlipayRequestDTO.getPartnerId();
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(partnerId, "code1");

			String merchantKey = resultMap.get("value");

			if (!StringUtil.isEmpty(merchantKey)) {
				return true;
			} else {
				if("cn".equals(language))
					wechatAlipayResponseDTO.setResultMsg(getMessage());
				else
					wechatAlipayResponseDTO.setResultMsg(getMessageEn());
				wechatAlipayResponseDTO.setResultCode(getMessageId());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if("cn".equals(language))
				wechatAlipayResponseDTO.setResultMsg(getMessage());
			else
				wechatAlipayResponseDTO.setResultMsg(getMessageEn());
			wechatAlipayResponseDTO.setResultCode(getMessageId());
			return false;
		}

	}

}
