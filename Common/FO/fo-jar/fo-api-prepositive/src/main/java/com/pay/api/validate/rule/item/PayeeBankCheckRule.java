/**
 *  File: OutbankCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.api.validate.rule.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.dto.Bank;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.BankService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class PayeeBankCheckRule extends MessageRule {

	private BankService bankService;
	private Map<String, String> banks;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();
		String bankName = request.getBankName();
		

		// 付款到账户直接跳过验证
		if (PayType.ACCT.getValue() == request.getPayType()) {
			return true;
		}

		initBank();
		String bankCode = banks.get(bankName);
		if (StringUtil.isEmpty(bankName) || null == bankCode) {
			result.setErrorCode(ErrorCode.BANKNAME_INVALID);
			result.setErrorMsg(ErrorCode.BANKNAME_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setOrgCode(String.valueOf(bankCode));
			request.setResult(result);
			return true;
		}
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	private void initBank() {

		if (null == banks || banks.isEmpty()) {
			banks = new HashMap<String, String>();
			List<Bank> bankList = bankService.getWithdrawBanks();
			for (Bank bank : bankList) {
				banks.put(bank.getBankName(), bank.getBankId());
			}
		}
	}
}
