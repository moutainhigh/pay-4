/**
 *  File: Batch2AcctPayeeAcctCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.item;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class Batch2AcctPayeeAcctCheckRule extends MessageRule {

	private AccountQueryService accountQueryService;

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

		Long memberCode = result.getPayeeMemberCode();

		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(memberCode, 10);

		if (null == acctAttribDto || acctAttribDto.getFrozen() == 1) {
			result.setErrorCode(ErrorCode.MEMBERACCOUNT_INVALID);
			result.setErrorMsg(ErrorCode.MEMBERACCOUNT_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setPayeeAcctcode(acctAttribDto.getAcctCode());
			result.setPayeeAcctType(Integer.valueOf(acctAttribDto.getAcctType()));
			return true;
		}
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

}
