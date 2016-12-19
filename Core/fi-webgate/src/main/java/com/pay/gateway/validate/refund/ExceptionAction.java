/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
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

		RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
		RefundApiResponse refundApiResponse = refundApiRequest
				.getRefundApiResponse();

		String signType = refundApiRequest.getSignType();
		String charsetType = refundApiRequest.getCharset();
		String partnerId = refundApiRequest.getPartnerId();

		String signData = refundApiRequest.generateSign();

		String signMsg = "" ;
		if(StringUtils.isNotEmpty(charsetType) && charsetType.equals(CharsetTypeEnum.UTF8.getCode())){
			signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, signType, charsetType, partnerId);
		}

		refundApiResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
