/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauthcomp.api;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.PreauthCompApiRequest;
import com.pay.gateway.dto.PreauthCompApiResponse;
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

		PreauthCompApiRequest preauthCompApiRequest = (PreauthCompApiRequest) validateBean;
		PreauthCompApiResponse preauthCompApiResponse = preauthCompApiRequest
				.getPreauthCompApiResponse();

		try {
			String partnerId = preauthCompApiRequest.getPartnerId();
			MemberDto memberDto = memberService.queryMemberByMemberCode(Long
					.valueOf(partnerId));
			if (null != memberDto && memberDto.getStatus() == 1) {
				return true;
			} else {
				preauthCompApiResponse.setResultCode(getMessageId());
				preauthCompApiResponse.setResultMsg(getMessage());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			preauthCompApiResponse.setResultCode(getMessageId());
			preauthCompApiResponse.setResultMsg(getMessage());
			return false;
		}

	}

}
