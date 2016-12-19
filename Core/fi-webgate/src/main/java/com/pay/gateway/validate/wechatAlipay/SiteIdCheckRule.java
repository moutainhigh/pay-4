/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.wechatAlipay;

import java.util.List;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证提交时间
 */
public class SiteIdCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;
	private String messageEn;

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
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

		String partnerId = wechatAlipayRequestDTO.getPartnerId();
		String siteId = wechatAlipayRequestDTO.getSiteId();
		String language = wechatAlipayRequestDTO.getLanguage();
		
		if(StringUtil.isEmpty(siteId)){
			if("cn".equals(language))
				wechatAlipayResponseDTO.setResultMsg(getMessage());
			else
				wechatAlipayResponseDTO.setResultMsg(getMessageEn());
			wechatAlipayResponseDTO.setResultCode(getMessageId());
			return false;
		}

		List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
				partnerId, siteId.toLowerCase(), "1,6");

		if (null != map && !map.isEmpty()) {
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
