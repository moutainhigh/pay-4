/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.currencyrate.api;


import com.pay.gateway.dto.CurrencyRateQueryApiRequest;
import com.pay.gateway.dto.CurrencyRateQueryApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.CurrencyCodeEnum;

/**
 * 验证网关版本
 */
public class CurrencyCodeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CurrencyRateQueryApiRequest crosspayApiRequest = (CurrencyRateQueryApiRequest) validateBean;
		CurrencyRateQueryApiResponse crosspayApiResponse = crosspayApiRequest
				.getCurrencyRateQueryApiResponse();

		String currencyCode = crosspayApiRequest.getCurrency();

		if (CurrencyCodeEnum.isExists(currencyCode)) {
			return true;
		} else {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
