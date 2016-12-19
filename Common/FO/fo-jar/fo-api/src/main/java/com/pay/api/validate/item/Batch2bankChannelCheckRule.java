/**
 *  File: OutbankCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.api.validate.item;

import java.util.HashMap;
import java.util.Map;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.fo.order.common.OrderType;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class Batch2bankChannelCheckRule extends MessageRule {

	private ConfigBankService configBankService;

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

		if (payType == PayType.ACCT.getValue()) {
			return true;
		}

		String bankCode = request.getOrgCode();
		String bankChannel = getOutBankCode(bankCode);

		if (StringUtil.isEmpty(bankChannel)) {
			result.setErrorCode(ErrorCode.BUSINESS_INVALID);
			result.setErrorMsg(ErrorCode.BUSINESS_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setPayeeBankCode(bankCode);
			result.setChannelCode(bankChannel);
			return true;
		}
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
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
}
