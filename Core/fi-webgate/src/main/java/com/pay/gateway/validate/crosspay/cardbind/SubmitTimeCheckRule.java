/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import java.text.SimpleDateFormat;

import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证提交时间
 */
public class SubmitTimeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String submitTime = cardBindRequest.getSubmitTime();
		
		if(StringUtil.isEmpty(submitTime) || submitTime.trim().length() != 14) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg("Submit time is empty or length is not 14 bits : 商户提交时间为空或者长度不为14");
			return false;
		}
		
//		if(StringUtil.isEmpty(submitTime)) {
//			cardBindResponse.setResultCode(getMessageId());
//			cardBindResponse.setResultMsg("Submit time is empty : 订单提交时间为空");
//			return false;
//		}
		
		if(!StringUtil.isEmpty(submitTime) && submitTime.trim().length()>14) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHMMSS");
		try {
			sf.parse(submitTime);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
