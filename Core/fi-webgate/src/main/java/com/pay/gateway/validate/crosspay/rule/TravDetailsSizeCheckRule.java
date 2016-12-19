package com.pay.gateway.validate.crosspay.rule;

import com.pay.fi.commons.TradeMccEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class TravDetailsSizeCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String travDetailsSize = crosspayApiRequest.getTravDetailsSize();
		String mcc = crosspayApiRequest.getMcc();
		
		if(TradeMccEnum.AIRHOT_TRADE.getCode().equals(mcc) && 
				!StringUtil.isEmpty(travDetailsSize) && 
				travDetailsSize.trim().length() > 10) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());//商户回调地址为空
			return false;
		}

		return true;
	}
}
