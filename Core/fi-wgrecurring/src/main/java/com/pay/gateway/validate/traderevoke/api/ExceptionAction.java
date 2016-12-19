/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.traderevoke.api;


import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.TradeRevokeApiRequest;
import com.pay.gateway.dto.TradeRevokeApiResponse;
import com.pay.inf.rule.AbstractAction;

/**
 * 验证订单号
 */
public class ExceptionAction extends AbstractAction {

	private TradeDataSingnatureService tradeDataSingnatureService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {

		TradeRevokeApiRequest tradeRevokeApiRequest = (TradeRevokeApiRequest) validateBean;

		TradeRevokeApiResponse tradeRevokeApiResponse = tradeRevokeApiRequest
				.getTradeRevokeApiResponse();

		String signType = tradeRevokeApiRequest.getSignType();
		String charsetType = tradeRevokeApiRequest.getCharset();
		String partnerId = tradeRevokeApiRequest.getPartnerId();

		String signData = tradeRevokeApiResponse.generateSign();
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, signType, charsetType, partnerId);
		tradeRevokeApiResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
