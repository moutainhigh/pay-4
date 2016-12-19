package com.pay.gateway.validate.crosspay.rule;

import com.pay.gateway.dto.CancelRecurringRequest;
import com.pay.gateway.dto.CancelRecurringResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class CancelTypeCheckRule extends MessageRule {
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		
		CancelRecurringRequest cancelRecurringRequest=(CancelRecurringRequest)validateBean;
		CancelRecurringResponse cancelRecurringResponse = cancelRecurringRequest.getCancelRecurringResponse();
        String	cancelType = cancelRecurringRequest.getCancelType();
        if (!StringUtil.isEmpty(cancelType)) {
        	if(cancelType.equals("0") ||cancelType.equals("1")  ){
        		return true;
        	}
        	return false;
        } else {
			cancelRecurringResponse.setResultCode(getMessageId());
			cancelRecurringResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
