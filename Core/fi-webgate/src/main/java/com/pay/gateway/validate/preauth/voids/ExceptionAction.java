/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.voids;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.dto.preauth.PreauthVoidRequest;
import com.pay.gateway.dto.preauth.PreauthVoidResponse;
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
		PreauthVoidRequest crosspayApiRequest = (PreauthVoidRequest) validateBean;
		PreauthVoidResponse crosspayApiResponse = crosspayApiRequest.getPreauthVoidResponse();

		String signType = crosspayApiRequest.getSignType();
		String charsetType = crosspayApiRequest.getCharset();
		String partnerId = crosspayApiRequest.getPartnerId();
		
		if(StringUtil.isEmpty(partnerId)){
			partnerId="10000003681";
		}

		String signData = crosspayApiResponse.generateSign();
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
