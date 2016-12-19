package com.pay.gateway.validate.crosspay.api;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
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

		CancelRecurringRequest cancelRecurringRequest = (CancelRecurringRequest) validateBean;
		CancelRecurringResponse cancelRecurringResponse = cancelRecurringRequest
			.getCancelRecurringResponse();

		String partnerId = cancelRecurringRequest.getPartnerId();

		if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim()) ) {
			return true;
		} else {
			cancelRecurringResponse.setResultCode(getMessageId());
			cancelRecurringResponse.setResultMsg(getMessage());
			return false;
		}

	}
}
