/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.rule;

import org.apache.commons.lang.StringUtils;

import com.pay.gateway.dto.CrosspayApiRecurringRequest;
import com.pay.gateway.dto.CrosspayApiRecurringResponse;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 循环扣款的check
 */
public class RecurringCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CrosspayApiRecurringRequest  crosspayApiRecurringRequest = (CrosspayApiRecurringRequest) validateBean;
		CrosspayApiRecurringResponse crosspayApiRecurringResponse = crosspayApiRecurringRequest
				.getCrosspayApiRecurringResponse();
		String  recurringFlag="";
				recurringFlag= crosspayApiRecurringRequest.getRecurringFlag();
		if(recurringFlag.equals("1")){
			if(!crosspayApiRecurringResponse.getFrequency().equals("Y") //验证周期是年还是月
					&&!crosspayApiRecurringResponse.getFrequency().equals("M")){
				crosspayApiRecurringResponse.setResultMsg(getMessage());
				crosspayApiRecurringResponse.setResultCode(getMessageId());
				return false;
			}
			if(StringUtils.isNotEmpty(crosspayApiRecurringResponse.getPeriod()) //验证期数
					&&!StringUtils.isNumeric(crosspayApiRecurringResponse.getPeriod())){
				crosspayApiRecurringResponse.setResultMsg(getMessage());
				crosspayApiRecurringResponse.setResultCode(getMessageId());
				return false;
			}
			/*if(StringUtils.isNotEmpty(crosspayApiRecurringResponse.getNoticeUrl())){ //验证商户回调地址
				crosspayApiRecurringResponse.setResultMsg(getMessage());
				crosspayApiRecurringResponse.setResultCode(getMessageId());
				return false;
			}*/
			return true;
		}else{
			return true;
		}
	}		
}
