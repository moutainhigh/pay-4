package com.pay.gateway.validate.crosspay.cardbind;

import org.apache.commons.lang.StringUtils;

import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
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
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();
		
		String partnerId = cardBindRequest.getPartnerId();
		
		if(StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg("Partner id is emtpy or its length is more than 32");
			return false;
		}

		if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim()) && partnerId.trim().length() <= 32) {
			return true;
		} else {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
		}

	}
}
