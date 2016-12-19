/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.traderevoke.api;


import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.TradeRevokeApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;

/**
 * 验证网关金额
 */
public class OrderAmountCheckRule extends MessageRule {

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

		String orderAmount = tradeRevokeApiRequest.getOrderAmount();

		if (NumberUtil.isNumber(orderAmount)) {
			return true;
		} else {
			tradeRevokeApiResponse.setResultCode(getMessageId());
			tradeRevokeApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
