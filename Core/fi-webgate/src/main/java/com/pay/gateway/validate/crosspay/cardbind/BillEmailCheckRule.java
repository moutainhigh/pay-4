/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.IdentityUtil;
import com.pay.util.StringUtil;

/**
 * 账单邮箱校验。长度不能超过128个字符
 */
public class BillEmailCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String billEmail = cardBindRequest.getBillEmail();
		if (!StringUtil.isEmpty(billEmail)
					&&(!IdentityUtil.validateEmail(billEmail) ||
					billEmail.trim().length()>128)) {
				cardBindResponse.setResultCode(getMessageId());
				cardBindResponse.setResultMsg(getMessage());
				return false;
		}
		return true;
	}

}
