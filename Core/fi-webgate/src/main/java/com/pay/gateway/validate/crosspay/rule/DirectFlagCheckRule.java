package com.pay.gateway.validate.crosspay.rule;

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

		String directFlag = crosspayApiRequest.getDirectFlag();
		String tradeType = crosspayApiRequest.getTradeType();
		
		if(TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType)){
			if(!StringUtil.isEmpty(directFlag)&&directFlag.trim().length()==1) {
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
