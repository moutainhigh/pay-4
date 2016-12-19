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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.WechatAlipayRequest;
import com.pay.gateway.dto.WechatAlipayResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关签名
 */
public class CrosspayAcquireCashierSignMsgCheckRule extends MessageRule {

	private final Log logger = LogFactory.getLog(getClass());
	private TxncoreClientService txncoreClientService;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private String messageEn;
     
	public String getMessageEn() {
		return messageEn;
	}
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
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

		String orderId = wechatAlipayRequestDTO.getOrderId();
		String partnerId = wechatAlipayRequestDTO.getPartnerId();
		String signType = wechatAlipayRequestDTO.getSignType();
		String charsetType = wechatAlipayRequestDTO.getCharset();
		String signMsg = wechatAlipayRequestDTO.getSignMsg();

		String src = wechatAlipayRequestDTO.generateSign();
		String language = wechatAlipayRequestDTO.getLanguage();

		//if (logger.isInfoEnabled()) {
			logger.info("partnerId:" + partnerId + ",orderId:" + orderId
					+ "signMsg:" + signMsg);
			logger.info("system signData:" + src);
		//}

		try {
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(partnerId, "code1");

			String merchantKey = resultMap.get("value");

			boolean isvalid = tradeDataSingnatureService.verifyBySignType(src,
					signMsg, signType, merchantKey, charsetType);

			if (!isvalid) {
				if("cn".equals(language))
					wechatAlipayResponseDTO.setResultMsg(getMessage());
				else
					wechatAlipayResponseDTO.setResultMsg(getMessageEn());
				wechatAlipayResponseDTO.setResultCode(getMessageId());
			}
			return isvalid;
		} catch (Exception e) {
			logger.error("sign check error:", e);
			
			if("cn".equals(language))
				wechatAlipayResponseDTO.setResultMsg(getMessage());
			else
				wechatAlipayResponseDTO.setResultMsg(getMessageEn());
			wechatAlipayResponseDTO.setResultCode(getMessageId());
			return false;
		}
	}

}
