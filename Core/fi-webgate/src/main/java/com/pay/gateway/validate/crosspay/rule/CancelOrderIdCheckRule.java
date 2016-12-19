package com.pay.gateway.validate.crosspay.rule;

import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class CancelOrderIdCheckRule extends MessageRule{

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		
		CancelRecurringRequest cancelRecurringRequest=(CancelRecurringRequest)validateBean;
		CancelRecurringResponse cancelRecurringResponse = cancelRecurringRequest.getCancelRecurringResponse();
        String	cancelOrderId = cancelRecurringRequest.getCancelOrderId();
        if (!StringUtil.isEmpty(cancelOrderId)) {
			return true;
		} else {
			cancelRecurringResponse.setResultCode(getMessageId());
			cancelRecurringResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
