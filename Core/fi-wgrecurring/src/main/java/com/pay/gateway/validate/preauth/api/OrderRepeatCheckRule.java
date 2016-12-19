/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;


import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
import com.pay.gateway.model.GatewayRequest;
import com.pay.gateway.service.GatewayRequestService;
import com.pay.inf.rule.MessageRule;

/**
 * 验证订单号重复
 */
public class OrderRepeatCheckRule extends MessageRule {

	private GatewayRequestService gatewayRequestService;

	public void setGatewayRequestService(
			GatewayRequestService gatewayRequestService) {
		this.gatewayRequestService = gatewayRequestService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preauthApiResponse = preauthApiRequest
				.getPreauthApiResponse();

		String orderId = preauthApiRequest.getOrderId();
		String memberCode = preauthApiRequest.getPartnerId();

		GatewayRequest gatewayRequest = gatewayRequestService
				.findGatewayRequest(Long.valueOf(memberCode), orderId);
		if (null == gatewayRequest) {
			return true;
		} else {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

}
