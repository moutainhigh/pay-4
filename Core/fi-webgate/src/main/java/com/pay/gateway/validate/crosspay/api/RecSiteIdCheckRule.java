/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.api;

import java.util.List;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.gateway.dto.CrosspayApiRecurringRequest;
import com.pay.gateway.dto.CrosspayApiRecurringResponse;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证提交时间
 */
public class RecSiteIdCheckRule extends MessageRule {

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
	
		CancelRecurringRequest cancelRecurringRequest=(CancelRecurringRequest)validateBean;
		CancelRecurringResponse cancelRecurringResponse = cancelRecurringRequest.getCancelRecurringResponse();
		String partnerId = cancelRecurringRequest.getPartnerId();
		String siteId = cancelRecurringRequest.getSiteId();

		if (StringUtil.isEmpty(siteId)) {
			cancelRecurringResponse.setResultCode(getMessageId());
			cancelRecurringResponse.setResultMsg("Domain name can not be Empty");
			return false;
		}

		List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
				partnerId, siteId, "1");

		if (null != map && !map.isEmpty()) {
			return true;
		} else {
			cancelRecurringResponse.setResultCode(getMessageId());
			cancelRecurringResponse.setResultMsg(getMessage());
			return false;
	   }
	}
}
