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
import com.pay.util.StringUtil;

/**
 * 验证订单号
 */
public class OrderIdCheckRule extends MessageRule {

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

		String orderId = preauthCompApiRequest.getOrderId();

		if (!StringUtil.isEmpty(orderId)) {
			return true;
		} else {
			preauthCompApiResponse.setResultCode(getMessageId());
			preauthCompApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
