package com.pay.gateway.validate.crosspay.api;

/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.inf.rule.AbstractAction;

/**
 * 验证订单号
 */
public class RecExceptionAction extends AbstractAction {

	private TradeDataSingnatureService tradeDataSingnatureService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {

		CancelRecurringRequest cancelRecurringRequest = (CancelRecurringRequest) validateBean;
		CancelRecurringResponse cancelRecurringResponse = cancelRecurringRequest.getCancelRecurringResponse();
		String signType = cancelRecurringRequest.getSignType();
		String charsetType = cancelRecurringRequest.getCharset();
		String partnerId = cancelRecurringRequest.getPartnerId();

		String signData = cancelRecurringResponse.generateSign();
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, signType, charsetType, partnerId);
		cancelRecurringResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
