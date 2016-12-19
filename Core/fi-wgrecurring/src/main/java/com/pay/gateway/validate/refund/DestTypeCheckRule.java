/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 验证订单号
 */
public class DestTypeCheckRule extends MessageRule {

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

		String destType = refundApiRequest.getDestType();

		if (!StringUtil.isEmpty(destType) && NumberUtil.isNumber(destType)
				&& ("1".equals(destType) || "2".equals(destType))) {
			return true;
		} else {
			refundApiResponse.setResultCode(getMessageId());
			refundApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
