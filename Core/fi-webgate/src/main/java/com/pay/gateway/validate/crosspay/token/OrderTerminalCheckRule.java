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
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class OrderTerminalCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
 
		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();

		String orderTerminal = crosspayApiRequest.getOrderTerminal();
		String tradeType = crosspayApiRequest.getTradeType();

		if(TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType)) {
			if(StringUtil.isEmpty(orderTerminal) || orderTerminal.trim().length() > 2)
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
		
		return true;
	}

}
