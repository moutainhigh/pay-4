/**
 *  File: CaculateFeeAction.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.item;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.helper.FeeType;
import com.pay.api.helper.PayType;
import com.pay.inf.rule.AbstractAction;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;

/**
 * 
 */
public class CaculateFeeAction extends AbstractAction {

	// 算费服务
	private AccountingService batchPay2bankAccountingService;
	private AccountingService batchPay2acctAccountingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {
		BatchPaymentItemRequest request = (BatchPaymentItemRequest) validateBean;
		BatchPaymentItemResult result = request.getResult();
		Long merchantCode = request.getMerchantCode();
		Long amount = request.getAmount();
		Integer payType = request.getPayType();
		Integer feeType = request.getFeeType();

		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(amount);
		accountingFeeRe.setPayer(merchantCode);
		AccountingFeeRes dealResponse = null;
		if (payType == PayType.ACCT.getValue()) {
			dealResponse = batchPay2acctAccountingService
					.caculateFee(accountingFeeRe);
		} else {
			dealResponse = batchPay2bankAccountingService
					.caculateFee(accountingFeeRe);
		}
		if (null != dealResponse) {
			result.setFee(dealResponse.getPayerFee());
		}

		if (feeType == FeeType.PAYER.getValue()) {
			result.setRealpayAmount(request.getAmount() + result.getFee());
			result.setRealoutAmount(request.getAmount());
		} else {
			result.setRealpayAmount(request.getAmount());
			result.setRealoutAmount(request.getAmount() - result.getFee());
		}
	}

	public void setBatchPay2bankAccountingService(
			AccountingService batchPay2bankAccountingService) {
		this.batchPay2bankAccountingService = batchPay2bankAccountingService;
	}

	public void setBatchPay2acctAccountingService(
			AccountingService batchPay2acctAccountingService) {
		this.batchPay2acctAccountingService = batchPay2acctAccountingService;
	}

}
