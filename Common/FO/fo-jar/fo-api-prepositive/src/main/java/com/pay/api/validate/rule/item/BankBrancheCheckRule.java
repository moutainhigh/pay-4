/**
 *  File: Pay2bankKaihuCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.api.validate.rule.item;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class BankBrancheCheckRule extends MessageRule {

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
		
		String bankInfo = request.getBranche();

		if (StringUtil.isEmpty(bankInfo)) {
			result.setErrorCode(ErrorCode.BANKBRANCHE_INVALID);
			result.setErrorMsg(ErrorCode.BANKBRANCHE_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			return true;
		}

	}

}
