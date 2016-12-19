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
 * 预授权
 * 验证网关版本
 */
public class BillCityCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preauthApiResponse = preauthApiRequest.getPreauthApiResponse();

		String billCity = preauthApiRequest.getBillCity();

		if (!StringUtil.isEmpty(billCity)) {
			return true;
		} else {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
