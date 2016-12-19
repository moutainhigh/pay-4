/**
 *  File: TotalAmountCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import java.util.regex.Pattern;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class TotalCountCheckRule extends MessageRule {

	static String pattern = "^[0-9]+$";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String requestTotalCount = request.getTotalCount();
		boolean flag = Pattern.matches(pattern, requestTotalCount);
		if (flag) {
			result.setTotalCount(Integer.valueOf(requestTotalCount));
			return true;
		} else {
			result.setErrorCode(ErrorCode.AMOUNT_INVALID);
			result.setErrorMsg(ErrorCode.AMOUNT_INVALID_DESC);
			request.setResult(result);
			return flag;
		}
	}

}
