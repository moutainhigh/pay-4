/**
 *  File: PaymentPayerCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.api.validate;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class MerchantAcctCheckRule extends MessageRule {

	private AccountQueryService accountQueryService;

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
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(memberCode, 10);

		if (null == acctAttribDto || acctAttribDto.getFrozen() == 0 || acctAttribDto.getAllowOut() == 0) {
			result.setErrorCode(ErrorCode.MERCHANTACCOUNT_INVALID);
			result.setErrorMsg(ErrorCode.MERCHANTACCOUNT_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setPayerAcctcode(acctAttribDto.getAcctCode());
			return true;
		}

	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public boolean validateAcctStatus(MemberBaseInfoBO payer) {
		int status = payer.getStatus();
		if (status != MemberStatusEnum.NORMAL.getCode()) {
			return false;
		}
		return true;
	}
}
