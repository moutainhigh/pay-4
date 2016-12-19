package com.pay.gateway.validate.crosspay.rule;

import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class RecNoticeUrlCheckRule extends MessageRule {
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

		String noticeUrl = cancelRecurringRequest.getNoticeUrl();

		if (!StringUtil.isEmpty(noticeUrl)) {
			return true;
		} else {
			cancelRecurringResponse.setResultCode(getMessageId());
			cancelRecurringResponse.setResultMsg(getMessage());
			return false;
		}

	}
}
