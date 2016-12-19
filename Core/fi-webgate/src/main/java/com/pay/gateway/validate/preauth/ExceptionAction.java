/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.rule.AbstractAction;
import com.pay.util.CharsetTypeEnum;
import com.pay.util.StringUtil;

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

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;

		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String signType = crosspayApiRequest.getSignType();
		String charsetType = crosspayApiRequest.getCharset();
		String partnerId = crosspayApiRequest.getPartnerId();
		String tradeType = crosspayApiRequest.getTradeType();
		
		if(StringUtil.isEmpty(partnerId)){
			partnerId="10000003681";
		}
		
		String filterStr="requestId,origOrderId";
		
		//根据不同业务类型返回不同的内容
		if(TradeTypeEnum.PREAUTH_COMPLETED.getCode().equals(tradeType)){
			 filterStr="requestId,tradeType";
		}else if(TradeTypeEnum.PREAUTH_REVOCATION.getCode().equals(tradeType)){
			 filterStr="settlementCurrencyCode,orderId,currencyCode";
		}

		String signData = crosspayApiResponse.generateSign(filterStr);
		String signMsg = "" ;
		if(StringUtils.isNotEmpty(charsetType) && charsetType.equals(CharsetTypeEnum.UTF8.getCode())){
			signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					signData, signType, charsetType, partnerId);
		}
		crosspayApiResponse.setSignMsg(signMsg);
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}
}
