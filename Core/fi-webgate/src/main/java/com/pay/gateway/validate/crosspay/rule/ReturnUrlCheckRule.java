package com.pay.gateway.validate.crosspay.rule;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
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

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String returnUrl = crosspayApiRequest.getReturnUrl();
		String tradeType = crosspayApiRequest.getTradeType();
		
		if(TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType)){
			if(StringUtil.isEmpty(returnUrl)) {
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());//商户回调地址为空
				return false;
			}else{
				if(returnUrl.trim().length()>256) {
					crosspayApiResponse.setResultCode(getMessageId());
					crosspayApiResponse.setResultMsg(getMessage());
					return false;
				}
			}
		}
		return true;
	}
}
