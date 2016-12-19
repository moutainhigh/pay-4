/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证网关商户
 */
public class MerchantConfigureCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
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

		try {
			String partnerId = orderApiQueryRequest.getPartnerId();
			if(StringUtil.isEmpty(partnerId)){
				orderApiQueryResponse.setResultCode("0103");
				orderApiQueryResponse.setResultMsg("the partnerId can't be null: 商户号不能为空");
				return false;
			}

			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(partnerId, "code1");

			String merchantKey = resultMap.get("value");

			if (!StringUtil.isEmpty(merchantKey)) {
				return true;
			} else {
				orderApiQueryResponse.setResultCode(getMessageId());
				orderApiQueryResponse.setResultMsg(getMessage());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			orderApiQueryResponse.setResultCode(getMessageId());
			orderApiQueryResponse.setResultMsg(getMessage());
			return false;
		}

	}

}
