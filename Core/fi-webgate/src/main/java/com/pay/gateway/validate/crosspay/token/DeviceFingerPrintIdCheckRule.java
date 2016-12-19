package com.pay.gateway.validate.crosspay.token;

import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class DeviceFingerPrintIdCheckRule extends MessageRule {

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();
		String deviceFingerPrintId = crosspayApiRequest.getDeviceFingerprintId();

		if(StringUtil.isEmpty(deviceFingerPrintId) || deviceFingerPrintId.trim().length()  > 60) {
			crosspayApiResponse.setResultMsg(getMessage());
			crosspayApiResponse.setResultCode(getMessageId());
			return false;
		}

		return true;
	}

}
