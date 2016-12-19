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
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.gateway.model.GatewayRequest;
import com.pay.gateway.service.GatewayRequestService;
import com.pay.inf.rule.AbstractAction;
import com.pay.util.MapUtil;

/**
 * 验证订单号
 */
public class GatewayRequestSaveAction extends AbstractAction {

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

		CrosspayGatewayRequest onlineRequestDTO = (CrosspayGatewayRequest) validateBean;
		CrosspayGatewayResponse onlineResponseDTO = onlineRequestDTO
				.getGatewayResponseDTO();

		String requestIP = onlineRequestDTO.getCustomerIP();
		Long memberCode = Long.valueOf(onlineRequestDTO.getPartnerId());
		Long requestId = saveRequest(requestIP, onlineRequestDTO);
		//onlineResponseDTO.setRequestId(requestId);

		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(Long.valueOf(memberCode));
		onlineResponseDTO.setSellerName(memberBaseInfoBO.getName());
	}

	private Long saveRequest(String requestIP,
			CrosspayGatewayRequest crosspayRequestDTO) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.ACQUIRE.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(crosspayRequestDTO.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(crosspayRequestDTO
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(crosspayRequestDTO));
		gatewayRequest.setServiceVersion(crosspayRequestDTO.getVersion());
		gatewayRequest.setSignMsg(crosspayRequestDTO.getSignMsg());
		gatewayRequest.setSignType(Integer.valueOf(crosspayRequestDTO
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(crosspayRequestDTO
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}

}
