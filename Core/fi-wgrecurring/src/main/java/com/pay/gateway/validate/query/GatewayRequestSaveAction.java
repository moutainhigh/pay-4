/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.OrderApiQueryRequest;
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

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		String requestIP = "";
		saveRequest(requestIP, orderApiQueryRequest);
	}

	private Long saveRequest(String requestIP,
			OrderApiQueryRequest orderApiQueryRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.QUERY.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(orderApiQueryRequest.getQueryOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(orderApiQueryRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(orderApiQueryRequest));
		gatewayRequest.setServiceVersion(orderApiQueryRequest.getVersion());
		gatewayRequest.setSignMsg(orderApiQueryRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(orderApiQueryRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(orderApiQueryRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
