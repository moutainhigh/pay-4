/**
 *  File: PaymentPayerCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.api.validate;

import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class BatchPaymentPayerCheckRule extends MessageRule {

	private MemberQueryService memberQueryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		BatchPaymentResult result = request.getResult();

		Long memberCode = request.getMerchantCode();
		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				null, memberCode, null, null);

		if (null == memberInfoDto
				|| memberInfoDto.getStatus() != MemberStatusEnum.NORMAL
						.getCode()) {
			result.setErrorCode(ErrorCode.MERCHANTSTATUS_INVALID);
			result.setErrorMsg(ErrorCode.MERCHANTSTATUS_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setPayerName(memberInfoDto.getMemberName());
			result.setPayerLoginName(memberInfoDto.getLoginName());
			result.setPayerMemberType(memberInfoDto.getMemberType());
			result.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
			request.setResult(result);
			return true;
		}

	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
}
