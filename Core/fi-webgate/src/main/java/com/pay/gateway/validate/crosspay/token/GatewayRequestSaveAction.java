/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.token;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
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

		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();

		String requestIP = crosspayApiRequest.getCustomerIP();
		saveRequest(requestIP, crosspayApiRequest);
	}

	private Long saveRequest(String requestIP,
			TokenpayRequest crosspayApiRequest) {
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
		//针对支付链下单,不进行签名校验的处理
		if(null == crosspayApiRequest.getSignMsg() || "".equals(crosspayApiRequest.getSignMsg())){
			gatewayRequest.setSignMsg("支付链下单-null");
		}else{
			gatewayRequest.setSignMsg(crosspayApiRequest.getSignMsg());
		}
		gatewayRequest.setSignType(Integer.valueOf(crosspayApiRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(crosspayApiRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
