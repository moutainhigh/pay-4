/**
 *  File: PaymentAmountCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.Map;

import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fundout.util.AmountUtils;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class PaymentAmountCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String requestAmount = detailRequest.getRequestAmount();

		if (!AmountUtils.checkAmount(requestAmount)
				|| AmountUtils.toLongAmount(requestAmount) <= 0) {
			
			Map paraMap = new HashMap();
			paraMap.put("row", detailRequest.getRow());
			String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);
			
			detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
			detailRequest.getPaymentResponse().setPaymentAmount(0L);
			return false;
		} else {
			Long amount = AmountUtils.toLongAmount(requestAmount);
			detailRequest.getPaymentResponse().setPaymentAmount(amount);
			return true;
		}

	}

}
