/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.completed;


import com.pay.gateway.dto.preauth.PreauthCompletedRequest;
import com.pay.gateway.dto.preauth.PreauthCompletedResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class RemarkCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PreauthCompletedRequest crosspayApiRequest = (PreauthCompletedRequest) validateBean;
		PreauthCompletedResponse crosspayApiResponse = crosspayApiRequest.getPreauthCompletedResponse();

		String remark = crosspayApiRequest.getRemark();
		if(!StringUtil.isEmpty(remark)&&remark.trim().length()>256) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
		
		return true;
	}

}
