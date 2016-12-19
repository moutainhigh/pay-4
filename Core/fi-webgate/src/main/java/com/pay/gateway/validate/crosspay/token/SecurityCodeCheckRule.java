/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.token;


import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class SecurityCodeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		return true;
//		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
//		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();
//		String securityCode = crosspayApiRequest.g
//
//		if (!StringUtil.isEmpty(securityCode)
//				              &&NumberUtil.isNumber(securityCode)) {
//			if(securityCode.trim().length() > 3) {
//				crosspayApiResponse.setResultCode(getMessageId());
//				crosspayApiResponse.setResultMsg("安全码长度超过3位");
//				return false;
//			}
//			
//			return true;
//		} else {
//			crosspayApiResponse.setResultCode(getMessageId());
//			crosspayApiResponse.setResultMsg(getMessage());
//			return false;
//		}
	}

}
