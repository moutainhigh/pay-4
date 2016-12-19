/**
 *  File: PayTypeCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class PayTypeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String payType = request.getPayType();

		try {
			PayType payTypeObject = PayType
					.getPayType(Integer.valueOf(payType));
			if (null != payTypeObject) {
				return true;
			}
		} catch (Exception e) {
		}
		result.setErrorCode(ErrorCode.PAYTYPE_INVALID);
		result.setErrorMsg(ErrorCode.PAYTYPE_INVALID_DESC);
		request.setResult(result);
		return false;
	}

}
