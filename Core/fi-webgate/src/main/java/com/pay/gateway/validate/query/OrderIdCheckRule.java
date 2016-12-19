/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dao.GatewayRequestDAO;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.gateway.model.GatewayRequest;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

import java.util.List;

/**
 * 验证订单号
 */
public class OrderIdCheckRule extends MessageRule {
	private GatewayRequestDAO gatewayRequestDAO;

	public GatewayRequestDAO getGatewayRequestDAO() {
		return gatewayRequestDAO;
	}

	public void setGatewayRequestDAO(GatewayRequestDAO gatewayRequestDAO) {
		this.gatewayRequestDAO = gatewayRequestDAO;
	}

	/*
         * (non-Javadoc)
         *
         * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
         */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
				.getOrderApiQueryResponse();


		String mode = orderApiQueryRequest.getMode();
		String queryOrderId = orderApiQueryRequest.getQueryOrderId();
		String orderId = orderApiQueryRequest.getOrderId();
		String partId = orderApiQueryRequest.getPartnerId();


		if (StringUtil.isEmpty(queryOrderId)) {
			orderApiQueryResponse.setResultCode("0110");
			orderApiQueryResponse.setResultMsg("the orderQueryId can't be null: 查询订单ID不能为空");
			return false;
		}else if(queryOrderId.length() > 32){
			orderApiQueryResponse.setResultCode("0111");
			orderApiQueryResponse.setResultMsg("the orderQueryId length too large:  查询订单ID长度过长");
			return false;
		}

		if(StringUtil.isEmpty(partId)){
			orderApiQueryResponse.setResultCode("0113");
			orderApiQueryResponse.setResultMsg("partId can't be null:  商户号不能为空");
			return false;
		}
		if(!StringUtil.isNumber(partId)){
			orderApiQueryResponse.setResultCode("0114");
			orderApiQueryResponse.setResultMsg("partId is illegal parameter:  商户号非法参数");
			return false;
		}
		List<GatewayRequest> gatewayRequests = gatewayRequestDAO.queryGatewayRequest(Long.parseLong(partId),
				queryOrderId, BusinessType.QUERY.getType());
		if(null != gatewayRequests && gatewayRequests.size() > 0){
			orderApiQueryResponse.setResultCode("0112");
			orderApiQueryResponse.setResultMsg("repeat queryOrderId:重复的查询订单号");
			return false;
		}

		if ("1".equals(mode)
				&& (StringUtil.isEmpty(orderId))) {
			orderApiQueryResponse.setResultCode(getMessageId());
			orderApiQueryResponse.setResultMsg(getMessage());
			return false;
		}
		return true;
	}

}
