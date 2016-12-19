/**
 *  File: PayeeTypeCheckRule.java
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
import com.pay.api.helper.PayType;
import com.pay.api.helper.PayeeType;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class PayeeTypeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();

		// 付款到账户直接跳过验证
		if (PayType.ACCT.getValue() == request.getPayType()) {
			return true;
		}
		String payeeType = request.getPayeeType();

		try {
			PayeeType payeeTypeObj = PayeeType.getPayeeType(Integer
					.valueOf(payeeType));
			if (null != payeeTypeObj) {
				return true;
			}
		} catch (Exception e) {
		}
		result.setErrorCode(ErrorCode.PAYEETYPE_INVALID);
		result.setErrorMsg(ErrorCode.PAYEETYPE_INVALID_DESC);
		request.setResult(result);
		return false;
	}

}
