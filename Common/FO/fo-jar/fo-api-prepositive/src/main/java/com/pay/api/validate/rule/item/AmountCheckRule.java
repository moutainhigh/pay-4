/**
 *  File: AmountCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule.item;

import java.util.regex.Pattern;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class AmountCheckRule extends MessageRule {

	static String pattern = "^[0-9]+$";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = new PaymentItemResult();
		String amount = request.getAmount();
		boolean flag = Pattern.matches(pattern, amount);
		if (!flag) {
			result.setErrorCode(ErrorCode.AMOUNT_INVALID);
			result.setErrorMsg(ErrorCode.AMOUNT_INVALID_DESC);
			request.setResult(result);
		}
		return flag;
	}

}
