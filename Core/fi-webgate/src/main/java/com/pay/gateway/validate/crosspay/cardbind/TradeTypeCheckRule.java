/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关版本
 */
public class TradeTypeCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String type = cardBindRequest.getTradeType();
		if (TradeTypeEnum.CARD_BIND.getCode().equals(type)
				||TradeTypeEnum.CARD_UNBIND.getCode().equals(type)
				||TradeTypeEnum.CARD_BIND_API.getCode().equals(type)) {
			return true;
		} else {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
