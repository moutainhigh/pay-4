/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.dccrate;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.DccRateQueryRequest;
import com.pay.gateway.dto.DccRateQueryResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关签名
 */
public class CrosspayAcquireApiSignMsgCheckRule extends MessageRule {

	private final Log logger = LogFactory.getLog(getClass());
	private TxncoreClientService txncoreClientService;
	private TradeDataSingnatureService tradeDataSingnatureService;

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		DccRateQueryRequest crosspayApiRequest = (DccRateQueryRequest) validateBean;
		DccRateQueryResponse crosspayApiResponse = crosspayApiRequest.getDccRateQueryResponse();

		String orderId = crosspayApiRequest.getSerialNo();
		String partnerId = crosspayApiRequest.getPartnerId();
		String signType = crosspayApiRequest.getSignType();
		String charsetType = crosspayApiRequest.getCharset();
		String signMsg = crosspayApiRequest.getSignMsg();

		String src = crosspayApiRequest.generateSign();

		if (logger.isInfoEnabled()) {
			logger.info("partnerId:" + partnerId + ",orderId:" + orderId
					+ "signMsg:" + signMsg);
			logger.info("system signData:" + src);
		}

		try {

			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(partnerId, "code1");

			String merchantKey = resultMap.get("value");

			boolean isvalid = tradeDataSingnatureService.verifyBySignType(src,
					signMsg, signType, merchantKey, charsetType);

			if (!isvalid) {
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
			}
			return isvalid;
		} catch (Exception e) {
			logger.error("sign check error:", e);
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
