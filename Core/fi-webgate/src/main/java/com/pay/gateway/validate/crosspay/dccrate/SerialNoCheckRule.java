/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.dccrate;

import com.pay.gateway.dto.DccRateQueryRequest;
import com.pay.gateway.dto.DccRateQueryResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证订单号
 */
public class SerialNoCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		DccRateQueryRequest crosspayApiRequest = (DccRateQueryRequest) validateBean;
		DccRateQueryResponse crosspayApiResponse = crosspayApiRequest.getDccRateQueryResponse();
		String orderId = crosspayApiRequest.getSerialNo();

		if (StringUtil.isEmpty(orderId)||orderId.trim().length()>32) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
		
		return true;
	}

}
