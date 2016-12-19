/**
 *  File: Pay2bankKaihuCheckRule.java
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
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class Pay2bankKaihuCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String bankInfo = detailRequest.getPayeeOpeningBankName();

		if (StringUtil.isEmpty(bankInfo)) {
			Map paraMap = new HashMap();
			paraMap.put("row", detailRequest.getRow());
			String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);
			detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
			return false;
		} else {
			return true;
		}

	}

}
