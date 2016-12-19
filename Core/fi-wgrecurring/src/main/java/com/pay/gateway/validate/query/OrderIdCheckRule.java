/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
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

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
				.getOrderApiQueryResponse();

		String mode = orderApiQueryRequest.getMode();
		String queryOrderId = orderApiQueryRequest.getQueryOrderId();
		String orderId = orderApiQueryRequest.getOrderId();

		if (StringUtil.isEmpty(queryOrderId)) {
			orderApiQueryResponse.setResultCode(getMessageId());
			orderApiQueryResponse.setResultMsg(getMessage());
			return false;
		}

		if ("1".equals(mode)
				&& (StringUtil.isEmpty(orderId))) {
			orderApiQueryResponse.setResultCode(getMessageId());
			orderApiQueryResponse.setResultMsg(getMessage());
			return false;
		}
		return true;
	}

}
