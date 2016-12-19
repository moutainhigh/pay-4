/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.traderevoke.api;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.TradeRevokeApiRequest;
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

		TradeRevokeApiRequest tradeRevokeApiRequest = (TradeRevokeApiRequest) validateBean;

		String requestIP = tradeRevokeApiRequest.getCustomerIP();
		saveRequest(requestIP, tradeRevokeApiRequest);
	}

	private Long saveRequest(String requestIP,
			TradeRevokeApiRequest tradeRevokeApiRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.ACQUIRE.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(tradeRevokeApiRequest.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(tradeRevokeApiRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(tradeRevokeApiRequest));
		gatewayRequest.setServiceVersion(tradeRevokeApiRequest.getVersion());
		gatewayRequest.setSignMsg(tradeRevokeApiRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(tradeRevokeApiRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(tradeRevokeApiRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
