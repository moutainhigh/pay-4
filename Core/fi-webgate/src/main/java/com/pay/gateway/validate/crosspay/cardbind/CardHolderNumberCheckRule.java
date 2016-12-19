/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
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

		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String memberCode = cardBindRequest.getPartnerId();
		String cardHolderNumber = cardBindRequest.getCardHolderNumber();
		String tradeType = cardBindRequest.getTradeType();
		
		if((TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType))) {
			return true;
		}
		
		//卡绑定时，可以为空，然后收银台再输入
		if (StringUtil.isEmpty(cardHolderNumber)) {
			return true;
		}

		if (!StringUtil.isEmpty(cardHolderNumber) && cardHolderNumber.trim().length()<14) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}
		
		if (!StringUtil.isEmpty(cardHolderNumber) && cardHolderNumber.trim().length() > 19) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg("Card holder number is more than 19 bits : 卡号长度超过19位");
			return false;
		}

		boolean isHaveProduct = false;
		try {
			isHaveProduct = com.pay.gateway.validate.crosspay.rule.CardHolderNumberCheckRule.
					isHaveProduct(cardHolderNumber,memberCode,memberProductService);
			if (!isHaveProduct) {
				cardBindResponse.setResultCode(getMessageId());
				cardBindResponse.setResultMsg(getMessage());
			}

		} catch (Exception e) {
			logger.error("CardHolderNumberCheckRule error:", e);
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}

		return isHaveProduct;

	}
}
