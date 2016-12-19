/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.rule.bindcard;

import com.pay.gateway.dto.BindCardInfo;
import com.pay.gateway.dto.BindCardResult;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 持卡人卡号校验
 */
public class CardHolderNumberCheckRule extends MessageRule {
	
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
		String cardHolderNumber = paymentInfo.getCardHolderNumber();
		String language = paymentInfo.getLanguage();

		if (!StringUtil.isEmpty(cardHolderNumber)
				&& NumberUtil.isNumber(cardHolderNumber)) {
			return true;
		} else {
			BindCardResult paymentResult = paymentInfo.getBindCardResult();
			if (null == paymentResult) {
				paymentResult = new BindCardResult();
				paymentInfo.setBindCardResult(paymentResult);
			}

			if("cn".equals(language)) {
				paymentResult.setResultMsg(this.getMessage());
			} else {
				paymentResult.setResultMsg(getMessageEn());
			}
			paymentResult.setResultCode(getMessageId());
			return false;
		}
	}

}
