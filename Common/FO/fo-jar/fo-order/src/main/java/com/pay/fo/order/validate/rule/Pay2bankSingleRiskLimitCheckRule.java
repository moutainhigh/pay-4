/**
 *  File: Pay2bankSingleRiskLimitCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fundout.util.AmountUtils;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.rule.MessageRule;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * 
 */
public class Pay2bankSingleRiskLimitCheckRule extends MessageRule {

	static BigDecimal MULTIPLE = new BigDecimal("1000");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		RCLimitResultDTO rule = detailRequest.getRiskRule();
		String requestAmount = detailRequest.getRequestAmount();
		BigDecimal amount = new BigDecimal(requestAmount).multiply(MULTIPLE);

		// 增加判断金额必须大于0才能支付
		if (null != rule && rule.getSingleLimit() < amount.longValue()) {

			String singleAmount = AmountUtils.numberFormat(rule
					.getSingleLimit());
			Map paraMap = new HashMap();
			paraMap.put("limitAmount", singleAmount);
			paraMap.put("row", detailRequest.getRow());
			String errorMsg = getMessageId();

			detailRequest.getPaymentResponse().setErrorMsg(
					FreeMarkParseUtil.parseTemplate(errorMsg, paraMap));
			return false;
		}
		return true;
	}

}
