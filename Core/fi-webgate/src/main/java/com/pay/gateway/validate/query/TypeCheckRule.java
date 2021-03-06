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

/**
 * 验证网关版本
 */
public class TypeCheckRule extends MessageRule {

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
		String type = orderApiQueryRequest.getType();
		boolean flag = false;
		if("3".equals(mode)){
			if("4".equals(type)){
				flag = true;
			}
		}else{
			if ("1".equals(type) || "2".equals(type) || "4".equals(type)|| "5".equals(type)|| "6".equals(type)|| "7".equals(type)) {
				flag = true;
			} 
		}
		if (flag) {
			return true;
		} else {
			orderApiQueryResponse.setResultCode(getMessageId());
			orderApiQueryResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
