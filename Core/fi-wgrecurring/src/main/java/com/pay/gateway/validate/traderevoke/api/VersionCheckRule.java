/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.traderevoke.api;

import com.pay.fi.commons.RequestVersionEnum;
import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.TradeRevokeApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关版本
 */
public class VersionCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		TradeRevokeApiRequest tradeRevokeApiRequest = (TradeRevokeApiRequest) validateBean;
		TradeRevokeApiResponse preauthApiResponse = tradeRevokeApiRequest.getTradeRevokeApiResponse();

		String version = tradeRevokeApiRequest.getVersion();

		if (RequestVersionEnum.ONLINE_1_0.getCode().equals(version)) {
			return true;
		} else {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
