/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证提交时间
 */
public class CardHolderNumberCheckRule extends MessageRule {

	private Log logger = LogFactory.getLog(getClass());
	protected MemberProductService memberProductService;
	public void setMemberProductService(
			MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String memberCode = crosspayApiRequest.getPartnerId();
		String cardHolderNumber = crosspayApiRequest.getCardHolderNumber();
		String tradeType = crosspayApiRequest.getTradeType();
		
		if((TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType))
				||TradeTypeEnum.PREAUTH_CASH.getCode().equals(tradeType)) {
			return true;
		}
		

		if (StringUtil.isEmpty(cardHolderNumber)
				||cardHolderNumber.trim().length()<14) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
		
		if (cardHolderNumber.trim().length() > 19) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}



		boolean isHaveProduct = false;
		try {
			isHaveProduct = com.pay.gateway.validate.crosspay.rule.CardHolderNumberCheckRule.
					isHaveProduct(cardHolderNumber,memberCode,memberProductService);
			if (!isHaveProduct) {
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
			}

		} catch (Exception e) {
			logger.error("CardHolderNumberCheckRule error:", e);
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}

		return isHaveProduct;
	}

	public static void main(String[] args) {
		String cardPrefix = "4392268309907674".substring(0, 6);
		System.out.println(cardPrefix);
	}
}
