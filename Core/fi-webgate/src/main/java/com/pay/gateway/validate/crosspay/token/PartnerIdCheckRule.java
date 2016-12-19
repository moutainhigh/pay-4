package com.pay.gateway.validate.crosspay.token;

import org.apache.commons.lang.StringUtils;

import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class PartnerIdCheckRule extends MessageRule {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();

		String partnerId = crosspayApiRequest.getPartnerId();
		
		if(StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg("Partner id is emtpy or its length is more than 32");
			return false;
		}

		if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim()) ) {
			return true;
		} else {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}

	}
}
