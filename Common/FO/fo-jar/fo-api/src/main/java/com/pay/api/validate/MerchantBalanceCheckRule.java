/**
 *  File: MerchantBalanceCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.FeeType;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class MerchantBalanceCheckRule extends MessageRule {

	private AccountQueryService accountQueryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;

		// 如果复核，则不判断余额
//		Integer auditType = request.getAuditFlag();
//		if (auditType == AuditFlag.NO.getValue()) {
//			return true;
//		}

		BatchPaymentResult result = request.getResult();
		Long memberCode = request.getMerchantCode();
		Integer feeType = request.getFeeType();
		Long successAmount = result.getSuccessAmount();
		Long totalFee = result.getTotalFee();

		Long totalAmount = 0L;
		if (feeType == FeeType.PAYER.getValue()) {
			totalAmount = successAmount + totalFee;
		} else {
			totalAmount = successAmount;
		}

		BalancesDto balanceDto = accountQueryService.doQueryBalancesNsTx(
				memberCode, 10);
		if (null != balanceDto && balanceDto.getBalance() >= totalAmount) {
			return true;
		} else {
			result.setErrorCode(ErrorCode.MERCHANTBALANCE_NOTENOUGH);
			result.setErrorMsg(ErrorCode.MERCHANTBALANCE_NOTENOUGH_DESC);
			request.setResult(result);
			return false;
		}

	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

}
