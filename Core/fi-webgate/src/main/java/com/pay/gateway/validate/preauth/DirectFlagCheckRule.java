package com.pay.gateway.validate.preauth;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
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
		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();
		String tradeType = crosspayApiRequest.getTradeType();

		String directFlag = crosspayApiRequest.getDirectFlag();
		
		if(TradeTypeEnum.PREAUTH_CASH.getCode().equals(tradeType)||
				TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType)){
			if(!StringUtil.isEmpty(directFlag)) {
				return true;
			}else{
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
		}
		return true;
	}
}
