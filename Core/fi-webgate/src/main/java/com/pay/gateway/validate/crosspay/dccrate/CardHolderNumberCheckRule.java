/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.dccrate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.gateway.dto.DccRateQueryRequest;
import com.pay.gateway.dto.DccRateQueryResponse;
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
		DccRateQueryRequest crosspayApiRequest = (DccRateQueryRequest) validateBean;
		DccRateQueryResponse crosspayApiResponse = crosspayApiRequest.getDccRateQueryResponse();

		String memberCode = crosspayApiRequest.getPartnerId();
		String cardHolderNumber = crosspayApiRequest.getCardHolderNumber();
		
		if (StringUtil.isEmpty(cardHolderNumber)
				||cardHolderNumber.trim().length()<14
				||cardHolderNumber.trim().length() > 19) {
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
}
