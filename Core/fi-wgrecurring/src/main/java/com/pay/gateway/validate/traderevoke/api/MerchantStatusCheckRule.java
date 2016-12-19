/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.traderevoke.api;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.TradeRevokeApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关商户
 */
public class MerchantStatusCheckRule extends MessageRule {

	private MemberService memberService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		TradeRevokeApiRequest tradeRevokeApiRequest = (TradeRevokeApiRequest) validateBean;
		TradeRevokeApiResponse tradeRevokeApiResponse = tradeRevokeApiRequest
				.getTradeRevokeApiResponse();

		try {
			String partnerId = tradeRevokeApiRequest.getPartnerId();
			MemberDto memberDto = memberService.queryMemberByMemberCode(Long
					.valueOf(partnerId));
			if (null != memberDto && memberDto.getStatus() == 1) {
				return true;
			} else {
				tradeRevokeApiResponse.setResultCode(getMessageId());
				tradeRevokeApiResponse.setResultMsg(getMessage());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			tradeRevokeApiResponse.setResultCode(getMessageId());
			tradeRevokeApiResponse.setResultMsg(getMessage());
			return false;
		}

	}

}
