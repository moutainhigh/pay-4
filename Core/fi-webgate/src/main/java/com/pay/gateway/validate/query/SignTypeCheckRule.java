/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import com.pay.fi.commons.SignTypeEnum;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关版本
 */
public class SignTypeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
				.getOrderApiQueryResponse();

		String signType = orderApiQueryRequest.getSignType();

		if (SignTypeEnum.isExists(signType)) {
			return true;
		} else {
			orderApiQueryResponse.setResultCode(getMessageId());
			orderApiQueryResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
