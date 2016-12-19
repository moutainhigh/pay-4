/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
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

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
				.getOrderApiQueryResponse();

		String signType = orderApiQueryRequest.getSignType();
		String charsetType = orderApiQueryRequest.getCharset();
		String partnerId = orderApiQueryRequest.getPartnerId();

		String signData = orderApiQueryRequest.generateSign();
		String signMsg =  null;
		try{
			if(StringUtils.isNotEmpty(charsetType) && charsetType.equals(CharsetTypeEnum.UTF8.getCode())){
				signMsg = tradeDataSingnatureService.genSignMsgBySignType(
						signData, signType, charsetType, partnerId);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		orderApiQueryResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
