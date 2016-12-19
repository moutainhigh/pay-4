/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauthcomp.api;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.PreauthCompApiRequest;
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

		PreauthCompApiRequest preauthCompApiRequest = (PreauthCompApiRequest) validateBean;

		String requestIP = preauthCompApiRequest.getCustomerIP();
		saveRequest(requestIP, preauthCompApiRequest);
	}

	private Long saveRequest(String requestIP,
			PreauthCompApiRequest preauthCompApiRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.PREAUTHCOMP.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(preauthCompApiRequest.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(preauthCompApiRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(preauthCompApiRequest));
		gatewayRequest.setServiceVersion(preauthCompApiRequest.getVersion());
		gatewayRequest.setSignMsg(preauthCompApiRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(preauthCompApiRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(preauthCompApiRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
