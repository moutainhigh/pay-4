/**
 *  File: OrderIdCheckRule.java
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
public class OrderIdCheckRule extends MessageRule {

	static String pattern = "^[A-Za-z0-9]+$";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();
		String orderId = request.getOrderId();

		boolean flag = Pattern.matches(pattern, orderId);
		if (flag) {
			return true;
		} else {
			result.setErrorCode(ErrorCode.ORDERID_INVALID);
			result.setErrorMsg(ErrorCode.ORDERID_INVALID_DESC);
			request.setResult(result);
			return flag;
		}
	}

}
