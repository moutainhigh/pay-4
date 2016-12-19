/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauthcomp.api;

import java.math.BigDecimal;

import com.pay.gateway.dto.PreauthCompApiRequest;
import com.pay.gateway.dto.PreauthCompApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;

/**
 * 验证网关金额
 */
public class TradeAmountCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PreauthCompApiRequest preauthCompApiRequest = (PreauthCompApiRequest) validateBean;
		PreauthCompApiResponse preauthApiResponse = preauthCompApiRequest
				.getPreauthCompApiResponse();

		String tradeAmount = preauthCompApiRequest.getTradeAmount();
		String orderAmount = preauthCompApiRequest.getOrderAmount();
		

		if (NumberUtil.isNumber(tradeAmount)) {
			BigDecimal oAmount = new BigDecimal(orderAmount);
			BigDecimal tAmount = new BigDecimal(tradeAmount);
			
			if(oAmount.compareTo(tAmount)<0){
				preauthApiResponse.setResultCode(getMessageId());
				preauthApiResponse.setResultMsg(getMessage());
				return false;
			}
			return true;
		} else {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
