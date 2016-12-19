/**
 *  File: MobileCheckRule.java
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
import com.pay.util.StringUtil;

/**
 * 
 */
public class MobileCheckRule extends MessageRule {

	static String pattern = "((1[3458]\\d{9})|((0\\d{2,3}){1}([1-9]\\d{6,7}){1}))";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();
		String mobile = request.getPayeeMobile();
		if (StringUtil.isEmpty(mobile)) {
			return true;
		}
		boolean flag = Pattern.matches(pattern, mobile);
		if (flag) {
			return true;
		} else {
			result.setErrorCode(ErrorCode.MOBILE_INVALID);
			result.setErrorMsg(ErrorCode.MOBILE_INVALID_DESC);
			request.setResult(result);
			return flag;
		}
	}

}
