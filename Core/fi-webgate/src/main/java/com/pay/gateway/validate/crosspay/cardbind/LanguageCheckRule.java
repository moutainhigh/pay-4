/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class LanguageCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String language = cardBindRequest.getLanguage();
		
		if(StringUtil.isEmpty(language)) {
			return true;
		}
		
		if(!StringUtil.isEmpty(language) && language.trim().equalsIgnoreCase("en")) {
			return true;
		}
		
		if(!StringUtil.isEmpty(language) && language.trim().equalsIgnoreCase("cn")) {
			return true;
		}
		
		cardBindResponse.setResultCode(getMessageId());
		cardBindResponse.setResultMsg(getMessage());
		return false;
	}
	
	
}
