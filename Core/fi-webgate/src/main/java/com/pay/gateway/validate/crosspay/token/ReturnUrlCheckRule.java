package com.pay.gateway.validate.crosspay.token;

import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class ReturnUrlCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();
		String returnUrl = crosspayApiRequest.getReturnUrl();
		String tradeType = crosspayApiRequest.getTradeType();

		if("1002".equals(tradeType) && StringUtil.isEmpty(returnUrl)) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());//商户回调地址为空
			return false;
		}

		return true;
	}
}
