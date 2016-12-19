/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import com.pay.fi.commons.RefundTypeEnum;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证订单号
 */
public class RefundRateCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
		RefundApiResponse refundApiResponse = refundApiRequest
				.getRefundApiResponse();

		String refundType = refundApiRequest.getRefundType();
		String refundAmount = refundApiRequest.getRefundAmount();
		if (refundType.equals(String.valueOf(RefundTypeEnum.RATE_REFUND
				.getCode()))) {
			if (refundAmount.length() < 4) {
				refundApiResponse.setResultCode(getMessageId());
				refundApiResponse.setResultMsg(getMessage());
				return false;
			}
		}

		return true;
	}

}
