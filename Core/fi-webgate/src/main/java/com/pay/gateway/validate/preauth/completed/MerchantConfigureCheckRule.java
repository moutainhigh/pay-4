/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.completed;

import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.preauth.PreauthCompletedRequest;
import com.pay.gateway.dto.preauth.PreauthCompletedResponse;
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
		PreauthCompletedRequest crosspayApiRequest = (PreauthCompletedRequest) validateBean;
		PreauthCompletedResponse crosspayApiResponse = crosspayApiRequest.getPreauthCompletedResponse();

		try {
			String partnerId = crosspayApiRequest.getPartnerId();
			Map<String, String> resultMap = txncoreClientService
					.crosspayPartnerConfigQuery(partnerId, "code1");

			if (null != resultMap
					&& !StringUtil.isEmpty(resultMap.get("value"))) {
				return true;
			} else {
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}

	}

}
