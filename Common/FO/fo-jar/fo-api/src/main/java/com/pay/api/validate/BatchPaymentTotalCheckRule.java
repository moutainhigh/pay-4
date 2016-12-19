/**
 *  File: BatchPaymentTotalCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 26, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate;

import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class BatchPaymentTotalCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		BatchPaymentResult result = request.getResult();

		Long totalAmount = request.getTotalAmount();
		Integer totalCount = request.getTotalCount();

		Long successAmount = result.getSuccessAmount();
		Integer successCount = result.getSuccessCount();

		boolean flag = true;
		if (totalAmount.longValue() != successAmount.longValue()) {
			result.setErrorCode(ErrorCode.AMOUNT_INVALID);
			result.setErrorMsg(ErrorCode.AMOUNT_INVALID_DESC);
			flag = false;
		}

		if (totalCount.intValue() != successCount.intValue()) {
			result.setErrorCode(ErrorCode.AMOUNT_INVALID);
			result.setErrorMsg(ErrorCode.AMOUNT_INVALID_DESC);
			flag = false;
		}
		
		request.setResult(result);
		return flag;
	}

}
