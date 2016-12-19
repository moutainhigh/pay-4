/**
 *  File: BatchPaymentRiskCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 25, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate;

import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.AuditFlag;
import com.pay.api.helper.ErrorCode;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankOrderValidateService;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class BatchPaymentRiskCheckRule extends MessageRule {

	private BatchPay2BankOrderValidateService batchPay2BankOrderValidateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		BatchPaymentResult result = request.getResult();

		Long merchantCode = request.getMerchantCode();
		Long successAmount = result.getSuccessAmount();
		Integer successCount = result.getSuccessCount();
		Integer auditType = request.getAuditFlag();

		// 需要复核不判断
		if (auditType == AuditFlag.YES.getValue()) {
			return true;
		}

		String message = batchPay2BankOrderValidateService.validateRCLimitInfo(
				merchantCode, successAmount, successCount);
		if (!StringUtil.isEmpty(message)) {
			result.setErrorCode(ErrorCode.EXCEED_LIMIT);
			result.setErrorMsg(ErrorCode.EXCEED_LIMIT_DESC);
			request.setResult(result);
			return false;
		} else {
			return true;
		}
	}

	public void setBatchPay2BankOrderValidateService(
			BatchPay2BankOrderValidateService batchPay2BankOrderValidateService) {
		this.batchPay2BankOrderValidateService = batchPay2BankOrderValidateService;
	}

}
