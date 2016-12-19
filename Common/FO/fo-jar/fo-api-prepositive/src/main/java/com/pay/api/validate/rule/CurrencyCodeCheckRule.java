/**
 *  File: CurrencyCodeCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 23, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.CurrencyCode;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class CurrencyCodeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = new PaymentResult();
		String code = request.getCurrencyCode();
		try {
			CurrencyCode currencyCode = CurrencyCode.getCurrencyCode(Integer
					.valueOf(code));
			if (null != currencyCode) {
				return true;
			}
		} catch (Exception e) {
		}
		result.setErrorCode(ErrorCode.CURRENCYCODE_INVALID);
		result.setErrorMsg(ErrorCode.CURRENCYCODE_INVALID_DESC);
		request.setResult(result);
		return false;
	}

}
