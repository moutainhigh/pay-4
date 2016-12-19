/**
 *  File: PayeeNameCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule.item;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class PayeeNameCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();
		String payeeName = request.getPayeeName();
		if (StringUtil.isEmpty(payeeName)) {
			result.setErrorCode(ErrorCode.MEMBER_NAME_INVALID);
			result.setErrorMsg(ErrorCode.MEMBER_NAME_INVALID_DESC);
			request.setResult(result);
			return false;
		}
		return true;
	}

}
