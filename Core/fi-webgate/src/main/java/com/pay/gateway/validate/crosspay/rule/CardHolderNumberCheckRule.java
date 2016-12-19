/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.rule;

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
	static String CARD_VISA = "CARD_VISA";
	static String CARD_MASTER = "CARD_MASTER";
	static String CARD_AE = "CARD_AE";
	static String CARD_DC = "CARD_DC";
	static String CARD_JCB = "CARD_JCB";

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
		
		if((TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType))) {
			return true;
		}
		
		//卡号为空且交易类型为创建Token并收银台支付时不校验卡号，否则校验卡号
		if(StringUtil.isEmpty(cardHolderNumber) &&
				(TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(tradeType)||
						TradeTypeEnum.CARD_BIND.getCode().equals(tradeType)||
						TradeTypeEnum.CREATE_TOKEN_PREAUTH_CASH.getCode().equals(tradeType))){
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
			isHaveProduct = isHaveProduct(cardHolderNumber,memberCode,memberProductService);
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

	public static boolean isHaveProduct(String cardHolderNumber, String memberCode, MemberProductService memberProductService){
		String cardPrefix = cardHolderNumber.substring(0, 6);
		long cardPre = Long.valueOf(cardPrefix);
		boolean isHaveProduct = false;
		String cardType = null;
		if (cardPre >= 400000 && cardPre <= 499999) {
			cardType = CARD_VISA;
		} else if ((cardPre>=510000 && cardPre <=559999)||(cardPre>=222100 && cardPre <=272099)) {
			cardType = CARD_MASTER;
		}else if((cardPre>=352800 && cardPre <=358999)||(cardPre>=213100 &&
				cardPre <=213199)||(cardPre>=180000 && cardPre <=180099)||cardPre == 310000
				||cardPre == 334021){
			cardType = CARD_JCB;
		} else if (cardPre >= 340000 && cardPre <= 349999) {
			cardType = CARD_AE;
		} else if (cardPre >= 370000 && cardPre <= 379999) {
			cardType = CARD_AE;
		} else if (cardPre == 324000 || cardPre == 337941 ||cardPre == 337912 ||cardPre == 337913 ) {
			cardType = CARD_AE;
		}else if (cardPre >= 601100 && cardPre <= 601199) {
			cardType = CARD_DC;
		} else if (cardPre >= 644000 && cardPre <= 659999) {
			cardType = CARD_DC;
		} else if (cardPre == 601300) {
			cardType = CARD_DC;
		} else if (cardPre >= 380000 && cardPre <= 399999) {
			cardType = CARD_DC;
		}
		isHaveProduct = memberProductService.isHaveProduct(
				Long.valueOf(memberCode), cardType);
		return isHaveProduct;
	}
}
