package com.pay.gateway.validate.crosspay.api;

import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class DeviceFingerPrintIdCheckRule extends MessageRule {

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String deviceFingerPrintId = crosspayApiRequest.getDeviceFingerprintId();

		if(StringUtil.isEmpty(deviceFingerPrintId) || deviceFingerPrintId.trim().length()  > 60) {
			crosspayApiResponse.setResultMsg(getMessage());
			crosspayApiResponse.setResultCode(getMessageId());
			return false;
		}

		return true;
	}

}
