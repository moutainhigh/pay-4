/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
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

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preauthApiResponse = preauthApiRequest
				.getPreauthApiResponse();

		String memberCode = preauthApiRequest.getPartnerId();
		String cardHolderNumber = preauthApiRequest.getCardHolderNumber();

		if (StringUtil.isEmpty(cardHolderNumber)) {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		}
		boolean isHaveProduct = false;
		try {
			String cardPrefix = cardHolderNumber.substring(0, 6);
			long cardPre = Long.valueOf(cardPrefix);
			if (cardPre >= 400000 && cardPre <= 499999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_VISA);
			} else if ((cardPre>=510000 && cardPre <=559999)||(cardPre>=222100 && cardPre <=272099)) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_MASTER);
			} else if (cardPre >= 340000 && cardPre <= 349999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_AE);
			} else if (cardPre >= 370000 && cardPre <= 379999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_AE);
			} else if (cardPre >= 300000 && cardPre <= 305999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_DC);
			} else if (cardPre >= 309500 && cardPre <= 309599) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_DC);
			} else if (cardPre >= 360000 && cardPre <= 369999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_DC);
			} else if (cardPre >= 380000 && cardPre <= 399999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_DC);
			} else if (cardPre >= 352800 && cardPre <= 358999) {
				isHaveProduct = memberProductService.isHaveProduct(
						Long.valueOf(memberCode), CARD_DC);
			}

			if (!isHaveProduct) {
				preauthApiResponse.setResultCode(getMessageId());
				preauthApiResponse.setResultMsg("未授权的卡种");
			}

		} catch (Exception e) {
			logger.error("CardHolderNumberCheckRule error:", e);
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg("卡号非法");
			return false;
		}

		return isHaveProduct;
	}

	public static void main(String[] args) {
		String cardPrefix = "4392268309907674".substring(0, 6);
		System.out.println(cardPrefix);
	}
}
