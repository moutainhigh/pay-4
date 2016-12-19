/**
 *  File: RequestTimeCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import java.text.SimpleDateFormat;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class RequestTimeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String requestTime = request.getRequestTime();

		try {
			new SimpleDateFormat("yyyyMMddHHmmss").parse(requestTime);
			return true;
		} catch (Exception e) {
		}
		result.setErrorCode(ErrorCode.REQUESTTIME_INVALID);
		result.setErrorMsg(ErrorCode.REQUESTTIME_INVALID_DESC);
		request.setResult(result);
		return false;
	}

}
