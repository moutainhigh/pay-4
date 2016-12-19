/**
 *  File: VersionCheckRule.java
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
import com.pay.api.helper.Version;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class VersionCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String version = request.getVersion();

		if (Version.version.equalsIgnoreCase(version)) {
			return true;
		} else {
			result.setErrorCode(ErrorCode.VERSION_INVALID);
			result.setErrorMsg(ErrorCode.VERSION_INVALID_DESC);
			request.setResult(result);
			return false;
		}

	}

}
