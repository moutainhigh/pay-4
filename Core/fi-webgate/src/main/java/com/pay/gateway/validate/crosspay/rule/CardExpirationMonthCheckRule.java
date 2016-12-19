/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.rule;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 验证网关版本
 */
public class CardExpirationMonthCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String cardExpirationMonth = crosspayApiRequest
				.getCardExpirationMonth();
		String tradeType = crosspayApiRequest.getTradeType();
		
		if(TradeTypeEnum.REALTIME_API.getCode().equals(tradeType)||TradeTypeEnum.PREAUTH_API.getCode().equals(tradeType)){//如果是API的
			if (!StringUtil.isEmpty(cardExpirationMonth)
					&&NumberUtil.isNumber(cardExpirationMonth)) {
				int month=0;
				try{
					month = Integer.valueOf(cardExpirationMonth);
					if(month>12 || month <=0){
						crosspayApiResponse.setResultCode(getMessageId());
						crosspayApiResponse.setResultMsg(getMessage());
						return false;
					}
				}catch(Exception e){
					e.printStackTrace();
					crosspayApiResponse.setResultCode(getMessageId());
					crosspayApiResponse.setResultMsg(getMessage());
					return false;
				}
				return true;
			} else {			
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
		}
		return true;
	}
}
