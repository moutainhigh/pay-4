/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.model.GatewayRequest;
import com.pay.gateway.service.GatewayRequestService;
import com.pay.inf.rule.AbstractAction;
import com.pay.util.MapUtil;

/**
 * 验证订单号
 */
public class GatewayRequestSaveAction extends AbstractAction {

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
	protected void doExecute(Object validateBean) throws Exception {

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;

		String requestIP = preauthApiRequest.getCustomerIP();
		saveRequest(requestIP, preauthApiRequest);
	}

	private Long saveRequest(String requestIP,
			PreauthApiRequest preauthApiRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.PREAUTH.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(preauthApiRequest.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(preauthApiRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(preauthApiRequest));
		gatewayRequest.setServiceVersion(preauthApiRequest.getVersion());
		gatewayRequest.setSignMsg(preauthApiRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(preauthApiRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(preauthApiRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
