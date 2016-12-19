package com.pay.gateway.validate.localpay.china;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.ChinaLocalPayResponse;
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
		ChinaLocalPayRequest crosspayApiRequest = (ChinaLocalPayRequest) validateBean;
		ChinaLocalPayResponse crosspayApiResponse = crosspayApiRequest.getChinaLocalPayResponse();

		String returnUrl = crosspayApiRequest.getReturnUrl();
		String tradeType = crosspayApiRequest.getTradeType();
		if(TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType) ) {
			if(StringUtil.isEmpty(returnUrl)||returnUrl.trim().length()>256){
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());//商户回调地址为空
				return false;
			}
		}

		return true;
	}
}
