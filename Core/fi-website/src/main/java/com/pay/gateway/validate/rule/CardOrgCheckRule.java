/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.rule;

import com.pay.fi.commons.CardOrgEnum;
import com.pay.gateway.dto.PaymentInfo;
import com.pay.gateway.dto.PaymentResult;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 持卡人卡组织校验
 */
public class CardOrgCheckRule extends MessageRule {
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
		String cardLimit = paymentInfo.getCardLimit();
		String cardHolderNumber = paymentInfo.getCardHolderNumber();
        
		PaymentResult paymentResult = paymentInfo.getPaymentResult();
		String language = paymentInfo.getLanguage();
		
		if (StringUtil.isEmpty(cardLimit)) {
			return true;
		} else {
			if(paymentResult == null)
				  paymentResult = new PaymentResult();
			if(CardOrgEnum.VISA.getCode().equals(cardLimit)){
				if(!cardHolderNumber.startsWith("4")){
					if("cn".equals(language))
						paymentResult.setErrorMsg(getMessage());
					else
						paymentResult.setErrorMsg(getMessageEn());
					
					paymentResult.setErrorCode(getMessageId());
					return false;
				}
			}else if(CardOrgEnum.MASTERCARD.getCode().equals(cardLimit)){
				if(!cardHolderNumber.startsWith("5")){
					paymentResult.setErrorCode(getMessageId());
					
					if("cn".equals(language))
						paymentResult.setErrorMsg(getMessage());
					else
						paymentResult.setErrorMsg(getMessageEn());
					
					paymentResult.setErrorCode(getMessageId());
					
					return false;
				}
			}
			
			return true;
		}
	}
}
