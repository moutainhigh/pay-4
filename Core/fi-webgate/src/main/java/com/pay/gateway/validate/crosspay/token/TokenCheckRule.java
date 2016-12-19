/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.token;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class TokenCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();
		String tradeType = crosspayApiRequest.getTradeType();
		String token = crosspayApiRequest.getToken();
		
		if(TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType)){
			if(StringUtil.isEmpty(token) || token.trim().length() > 128){
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
		}

		if(!StringUtil.isEmpty(token) && token.trim().length() > 128){
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg("Token is more than 128 bits : Token值超过128位");
			return false;
		}
		
		return true;
	}
}
