/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.currencyrate.api;

import com.pay.fi.commons.SignTypeEnum;
import com.pay.gateway.dto.CurrencyRateQueryApiRequest;
import com.pay.gateway.dto.CurrencyRateQueryApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关版本
 */
public class SignTypeCheckRule extends MessageRule {

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

		String signType = crosspayApiRequest.getSignType();

		if (SignTypeEnum.isExists(signType)) {
			return true;
		} else {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
