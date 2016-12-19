/**
 *  File: FeeTypeCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 23, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.FeeType;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class FeeTypeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String feeType = request.getFeeType();

		try {
			FeeType feeObject = FeeType.getFeeType(Integer.valueOf(feeType));
			if (null != feeObject) {
				return true;
			}
		} catch (Exception e) {
		}
		result.setErrorCode(ErrorCode.FEETYPE_INVALID);
		result.setErrorMsg(ErrorCode.FEETYPE_INVALID_DESC);
		request.setResult(result);
		return false;
	}

}
