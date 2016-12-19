/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.traderevoke.api;

import java.util.List;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.TradeRevokeApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证提交时间
 */
public class SiteIdCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;

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

		TradeRevokeApiRequest tradeRevokeApiRequest = (TradeRevokeApiRequest) validateBean;
		TradeRevokeApiResponse tradeRevokeApiResponse = tradeRevokeApiRequest
				.getTradeRevokeApiResponse();

		String partnerId = tradeRevokeApiRequest.getPartnerId();
		String siteId = tradeRevokeApiRequest.getSiteId();

		List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
				partnerId, siteId, "1");

		if (null != map && !map.isEmpty()) {
			return true;
		} else {
			tradeRevokeApiResponse.setResultCode(getMessageId());
			tradeRevokeApiResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
