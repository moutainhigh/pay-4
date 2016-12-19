/**
 *  File: OutbankCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.dto.Bank;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.BankService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class Batch2bankOutbankCheckRule extends MessageRule {

	private BankService bankService;
	private Map<String, String> banks;
	private ConfigBankService configBankService;

	public Batch2bankOutbankCheckRule(BankService bankService) {
		this.bankService = bankService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		initBank();
		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String bankName = detailRequest.getPayeeBankName();
		String bankCode = banks.get(bankName);

		if (StringUtil.isEmpty(bankName) || null == bankCode) {
			detailRequest.getPaymentResponse().setErrorMsg(
					constructErrorMsg(bankName,detailRequest.getRow()));
			return false;
		} else {
			String bankChannel = getOutBankCode(bankCode);
			if (StringUtil.isEmpty(bankChannel)) {
				detailRequest.getPaymentResponse().setErrorMsg(
						constructErrorMsg(bankName,detailRequest.getRow()));
				return false;
			} else {
				detailRequest.getPaymentResponse().setBankChannelCode(
						bankChannel);
				detailRequest.getPaymentResponse().setPayeeBankCode(bankCode);
				return true;
			}
		}
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
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

	private String getOutBankCode(String bankCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 目的银行编号
		map.put("targetBankId", bankCode);
		// 出款方式
		map.put("foMode", "1");
		// 出款业务
		map.put("fobusiness", OrderType.BATCHPAY2BANK.getValue());// 3 付款到银行

		return configBankService.queryFundOutBank2Withdraw(map);
	}

	private String constructErrorMsg(final String bankName,Integer row) {
		String errorMsg = getMessageId();
		Map paraMap = new HashMap();
		paraMap.put("bankName", bankName);
		paraMap.put("row", row);
		return FreeMarkParseUtil.parseTemplate(errorMsg, paraMap);
	}
}
