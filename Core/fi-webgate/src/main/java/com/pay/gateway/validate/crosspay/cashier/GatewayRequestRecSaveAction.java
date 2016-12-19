/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cashier;

import java.util.Date;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.gateway.model.GatewayRequest;
import com.pay.gateway.service.GatewayRequestService;
import com.pay.inf.rule.AbstractAction;
import com.pay.util.MapUtil;

/**
 * 验证订单号
 */
public class GatewayRequestRecSaveAction extends AbstractAction {

	private GatewayRequestService gatewayRequestService;
	private MemberQueryService memberQueryService;

	public void setGatewayRequestService(
			GatewayRequestService gatewayRequestService) {
		this.gatewayRequestService = gatewayRequestService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {

		CancelRecurringRequest cancelRecurringRequest = (CancelRecurringRequest) validateBean;
		CancelRecurringResponse cancelRecurringResponse = cancelRecurringRequest
			.getCancelRecurringResponse();
			
		Long memberCode = Long.valueOf(cancelRecurringRequest.getPartnerId());
		Long requestId = saveRequest(cancelRecurringRequest);
		//onlineResponseDTO.setRequestId(requestId);

		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(Long.valueOf(memberCode));
	}

	private Long saveRequest(CancelRecurringRequest cancelRecurringRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.RECURRING.getType());
		gatewayRequest.setServiceVersion(cancelRecurringRequest.getVersion());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setOrderId(cancelRecurringRequest.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(cancelRecurringRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(cancelRecurringRequest));
		gatewayRequest.setSignMsg(cancelRecurringRequest.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(cancelRecurringRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(cancelRecurringRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
