/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.rule.bindcard;

import com.pay.fi.commons.CardOrgEnum;
import com.pay.gateway.dto.BindCardInfo;
import com.pay.gateway.dto.BindCardResult;
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

		BindCardInfo paymentInfo = (BindCardInfo) validateBean;
		String cardLimit = paymentInfo.getCardLimit();
		String cardHolderNumber = paymentInfo.getCardHolderNumber();
        
		BindCardResult paymentResult = paymentInfo.getBindCardResult();
		String language = paymentInfo.getLanguage();
		
		if (StringUtil.isEmpty(cardLimit)) {
			return true;
		} else {
			if(paymentResult == null)
				  paymentResult = new BindCardResult();
			if(CardOrgEnum.VISA.getCode().equals(cardLimit)){
				if(!cardHolderNumber.startsWith("4")){
					if("cn".equals(language)) {
						paymentResult.setResultMsg(this.getMessage());
					} else {
						paymentResult.setResultMsg(getMessageEn());
					}
					paymentResult.setResultCode(getMessageId());
					return false;
				}
			}else if(CardOrgEnum.MASTERCARD.getCode().equals(cardLimit)){
				if(!cardHolderNumber.startsWith("5")){
					if("cn".equals(language)) {
						paymentResult.setResultMsg(this.getMessage());
					} else {
						paymentResult.setResultMsg(getMessageEn());
					}
					paymentResult.setResultCode(getMessageId());
					
					return false;
				}
			}
			
			return true;
		}
	}
}
