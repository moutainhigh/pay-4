package com.pay.gateway.validate.crosspay.token;

import com.pay.fi.commons.TradeMccEnum;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class DirectFlagCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();
		String directFlag = crosspayApiRequest.getDirectFlag();
		String tradeType = crosspayApiRequest.getTradeType();
		String mcc = crosspayApiRequest.getMcc();
		//String url = crosspayApiRequest.getURL();
		
		// gc accounts need no validation
		/*
		if(isGC(url)) {
			return true;
		}
		*/
		//trde type = 1001, need no validation
		if((TradeTypeEnum.REALTIME_API.getCode().equals(tradeType))) {
			return true;
		}

		if(!StringUtil.isEmpty(directFlag)) {
			return true;
		}
		
		crosspayApiResponse.setResultCode(getMessageId());
		crosspayApiResponse.setResultMsg(getMessage());
		return false;
	}
	/*
	private boolean isGC(String url) {
		return url.contains("gc") || url.contains("GC") || url.contains("Gc") || url.contains("gC");
	}
	*/
}
