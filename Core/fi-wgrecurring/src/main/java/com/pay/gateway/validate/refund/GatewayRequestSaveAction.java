/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import java.util.Date;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.refund.RefundApiRequest;
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

		RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
		String requestIP = refundApiRequest.getRequestIp();
		saveRequest(requestIP, refundApiRequest);
	}

	private Long saveRequest(String requestIP, RefundApiRequest refundApiRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.REFUND.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(refundApiRequest.getRefundOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(refundApiRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil.bean2String(refundApiRequest));
		gatewayRequest.setServiceVersion(refundApiRequest.getVersion());
		gatewayRequest.setSignMsg(refundApiRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(refundApiRequest
				.getSignType()));
		gatewayRequest
				.setCharset(Integer.valueOf(refundApiRequest.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
