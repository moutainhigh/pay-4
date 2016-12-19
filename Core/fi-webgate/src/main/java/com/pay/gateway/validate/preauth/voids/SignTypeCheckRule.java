/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.voids;

import com.pay.fi.commons.SignTypeEnum;
import com.pay.gateway.dto.preauth.PreauthVoidRequest;
import com.pay.gateway.dto.preauth.PreauthVoidResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

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
		PreauthVoidRequest crosspayApiRequest = (PreauthVoidRequest) validateBean;
		PreauthVoidResponse crosspayApiResponse = crosspayApiRequest.getPreauthVoidResponse();

		String signType = crosspayApiRequest.getSignType();

		if (SignTypeEnum.isExists(signType)&&!StringUtil.isEmpty(signType)) {
			return true;
		} else {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
