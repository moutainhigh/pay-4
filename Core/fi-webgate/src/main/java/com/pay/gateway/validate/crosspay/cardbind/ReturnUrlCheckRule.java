package com.pay.gateway.validate.crosspay.cardbind;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class ReturnUrlCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String returnUrl = cardBindRequest.getReturnUrl();
		String tradeType = cardBindRequest.getTradeType();

		if(TradeTypeEnum.CARD_BIND.getCode().equals(tradeType)
				&&StringUtil.isEmpty(returnUrl)) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}
		
		
		if(!StringUtil.isEmpty(returnUrl) && returnUrl.trim().length() > 256) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}

		return true;
	}
}
