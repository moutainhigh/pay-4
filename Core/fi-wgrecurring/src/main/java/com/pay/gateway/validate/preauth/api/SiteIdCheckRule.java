/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;

import java.util.List;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证提交时间
 */
public class SiteIdCheckRule extends MessageRule {

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

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preauthApiResponse = preauthApiRequest
				.getPreauthApiResponse();

		String partnerId = preauthApiRequest.getPartnerId();
		String siteId = preauthApiRequest.getSiteId();

		List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
				partnerId, siteId, "1");

		if (null != map && !map.isEmpty()) {
			return true;
		} else {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
