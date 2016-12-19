/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;


import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class CardHolderLastNameCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preatuhApiResponse = preauthApiRequest
				.getPreauthApiResponse();

		String cardHolderLastName = preauthApiRequest.getCardHolderLastName();

		if (!StringUtil.isEmpty(cardHolderLastName)) {
			return true;
		} else {
			preatuhApiResponse.setResultCode(getMessageId());
			preatuhApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
