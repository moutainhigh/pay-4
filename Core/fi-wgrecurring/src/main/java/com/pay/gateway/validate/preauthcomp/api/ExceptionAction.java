/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauthcomp.api;


import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.PreauthCompApiRequest;
import com.pay.gateway.dto.PreauthCompApiResponse;
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

		PreauthCompApiRequest preauthCompApiRequest = (PreauthCompApiRequest) validateBean;

		PreauthCompApiResponse preauthCompApiResponse = preauthCompApiRequest
				.getPreauthCompApiResponse();

		String signType = preauthCompApiRequest.getSignType();
		String charsetType = preauthCompApiRequest.getCharset();
		String partnerId = preauthCompApiRequest.getPartnerId();

		String signData = preauthCompApiResponse.generateSign();
		String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, signType, charsetType, partnerId);
		preauthCompApiResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
