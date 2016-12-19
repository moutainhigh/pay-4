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
 * 持卡人卡号校验
 */
public class CardExpirationMonthCheckRule extends MessageRule {
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
		String cardExpirationMonth = paymentInfo.getCardExpirationMonth();
		String language = paymentInfo.getLanguage();

		if (!StringUtil.isEmpty(cardExpirationMonth)
				&& NumberUtil.isNumber(cardExpirationMonth)) {
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
			
			paymentResult.setErrorCode(getMessageId());
			return false;
		}
	}

}
