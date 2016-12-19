/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.currencyrate.api;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.CurrencyRateQueryApiRequest;
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

		CurrencyRateQueryApiRequest crosspayApiRequest = (CurrencyRateQueryApiRequest) validateBean;

		String requestIP = crosspayApiRequest.getCustomerIP();
		saveRequest(requestIP, crosspayApiRequest);
	}

	private Long saveRequest(String requestIP,
			CurrencyRateQueryApiRequest crosspayApiRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.ACQUIRE.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(crosspayApiRequest.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(crosspayApiRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(crosspayApiRequest));
		gatewayRequest.setServiceVersion(crosspayApiRequest.getVersion());
		gatewayRequest.setSignMsg(crosspayApiRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(crosspayApiRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(crosspayApiRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
