/**
 *  File: PayeeAccountCheckRule.java
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
import com.pay.api.helper.PayType;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class PayeeAccountCheckRule extends MessageRule {

	static String mobile_pattern = "((1[3458]\\d{9})|((0\\d{2,3}){1}([1-9]\\d{6,7}){1}))";
	static String email_pattern = "\\w+([-+.]\\w+)*@([a-z0-9A-Z]+)([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	static String num_pattern = "^[0-9]+$";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();
		String payeeAccount = request.getPayeeAccount();

		if (StringUtil.isEmpty(payeeAccount)) {
			result.setErrorCode(ErrorCode.PAYEEACCOUNT_INVALID);
			result.setErrorMsg(ErrorCode.PAYEEACCOUNT_INVALID_DESC);
			request.setResult(result);
		}

		boolean flag = true;
		Integer payType = request.getPayType();
		if (payType == PayType.BANK.getValue()) {
			flag = Pattern.matches(num_pattern, payeeAccount);
		} else {
			flag = Pattern.matches(mobile_pattern, payeeAccount)
					|| Pattern.matches(email_pattern, payeeAccount);
		}

		if (!flag) {
			result.setErrorCode(ErrorCode.PAYEEACCOUNT_INVALID);
			result.setErrorMsg(ErrorCode.PAYEEACCOUNT_INVALID_DESC);
			request.setResult(result);
		}

		return flag;
	}

}
