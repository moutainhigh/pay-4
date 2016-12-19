/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.AbstractAction;
import com.pay.util.CharsetTypeEnum;

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
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String signType = cardBindRequest.getSignType();
		String charsetType = cardBindRequest.getCharset();
		String partnerId = cardBindRequest.getPartnerId();

		String signData = cardBindResponse.generateSign();
		String signMsg = "" ;
		if (String.valueOf(CharsetTypeEnum.UTF8.getCode()).equals(signType)) {
			signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, signType, charsetType, partnerId);
		}
		
		cardBindResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
