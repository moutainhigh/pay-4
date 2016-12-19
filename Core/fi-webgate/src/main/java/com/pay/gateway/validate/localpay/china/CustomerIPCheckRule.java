/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.localpay.china;


import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.ChinaLocalPayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.IPUtil;
import com.pay.util.StringUtil;

/**
 * 验证订单号
 */
public class CustomerIPCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		ChinaLocalPayRequest crosspayApiRequest = (ChinaLocalPayRequest) validateBean;
		ChinaLocalPayResponse crosspayApiResponse = crosspayApiRequest.getChinaLocalPayResponse();
		String customerIP = crosspayApiRequest.getCustomerIP();
		if (StringUtil.isEmpty(customerIP)||customerIP.trim().length()>32
				||!IPUtil.validateIp(customerIP)) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}	
		return true;
	}

}
