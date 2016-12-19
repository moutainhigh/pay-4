/**
 *  File: Batch2AcctPayeeCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.item;

import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class Batch2AcctPayeeCheckRule extends MessageRule {

	private MemberQueryService memberQueryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentItemRequest request = (BatchPaymentItemRequest) validateBean;
		BatchPaymentItemResult result = request.getResult();

		Integer payType = request.getPayType();
		if (payType == PayType.BANK.getValue()) {
			return true;
		}

		Long merchantCode = request.getMerchantCode();
		String payeeLoginName = request.getPayeeAccount();
		String payeeName = request.getPayeeName();

		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				payeeLoginName, null, null, null);

		if (null == memberInfoDto) {
			result.setErrorCode(ErrorCode.MEMBER_INVALID);
			result.setErrorMsg(ErrorCode.MEMBER_INVALID_DESC);
			request.setResult(result);
			return false;
		}

		Long memberCode = memberInfoDto.getMemberCode();
		// set payee info
		result.setPayeeMemberCode(memberCode);
		result.setPayeeMemberType(memberInfoDto.getMemberType());
		result.setPayeeName(payeeName);
		result.setPayeeLoginName(payeeLoginName);

		if (memberInfoDto.getStatus() != MemberStatusEnum.NORMAL.getCode()) {
			result.setErrorCode(ErrorCode.MEMBERSTATUS_INVALID);
			result.setErrorMsg(ErrorCode.MEMBERSTATUS_INVALID_DESC);
			request.setResult(result);
			return false;
		}

		if (merchantCode.longValue() == memberCode.longValue()) {
			result.setErrorCode(ErrorCode.MERCHANT_IS_MEMBER);
			result.setErrorMsg(ErrorCode.MERCHANT_IS_MEMBER_DESC);
			request.setResult(result);
			return false;
		}

		if (!payeeName.equals(memberInfoDto.getMemberName())) {
			result.setErrorCode(ErrorCode.MEMBER_NAME_INVALID);
			result.setErrorMsg(ErrorCode.MEMBER_NAME_INVALID_DESC);
			request.setResult(result);
			return false;
		}

		return false;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

}
