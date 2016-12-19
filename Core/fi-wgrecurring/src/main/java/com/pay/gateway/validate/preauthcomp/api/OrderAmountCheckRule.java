/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauthcomp.api;


import com.pay.gateway.dto.PreauthCompApiRequest;
import com.pay.gateway.dto.PreauthCompApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;

/**
 * 验证网关金额
 */
public class OrderAmountCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PreauthCompApiRequest preauthCompApiRequest = (PreauthCompApiRequest) validateBean;
		PreauthCompApiResponse preauthCompApiResponse = preauthCompApiRequest
				.getPreauthCompApiResponse();

		String orderAmount = preauthCompApiRequest.getOrderAmount();

		if (NumberUtil.isNumber(orderAmount)) {
			return true;
		} else {
			preauthCompApiResponse.setResultCode(getMessageId());
			preauthCompApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
