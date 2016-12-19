package com.pay.gateway.validate.localpay.china;
import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.ChinaLocalPayResponse;
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
		ChinaLocalPayRequest crosspayApiRequest = (ChinaLocalPayRequest) validateBean;
		ChinaLocalPayResponse crosspayApiResponse = crosspayApiRequest.getChinaLocalPayResponse();

		String directFlag = crosspayApiRequest.getDirectFlag();

		if(!StringUtil.isEmpty(directFlag)&&directFlag.trim().length()<=1) {
			return true;
		}
		
		crosspayApiResponse.setResultCode(getMessageId());
		crosspayApiResponse.setResultMsg(getMessage());
		return false;
	}
}
