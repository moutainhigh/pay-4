/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.rule;

import com.pay.gateway.dto.PaymentInfo;
import com.pay.gateway.dto.PaymentResult;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 持卡人安全码校验
 */
public class SecurityCodeCheckRule extends MessageRule {
	
	private String messageEn;
	
	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentInfo paymentInfo = (PaymentInfo) validateBean;
		String securityCode = paymentInfo.getSecurityCode();
		String language = paymentInfo.getLanguage();

		if (!StringUtil.isEmpty(securityCode)
				&& NumberUtil.isNumber(securityCode)) {
			return true;
		} else {
			PaymentResult paymentResult = paymentInfo.getPaymentResult();
			if (null == paymentResult) {
				paymentResult = new PaymentResult();
				paymentInfo.setPaymentResult(paymentResult);
			}
            
			if("cn".equals(language))
				paymentResult.setErrorMsg(getMessage());
			else
				paymentResult.setErrorMsg(getMessageEn());
			
			paymentResult.setErrorMsg(getMessageId());
			
			return false;
		}
	}

}
