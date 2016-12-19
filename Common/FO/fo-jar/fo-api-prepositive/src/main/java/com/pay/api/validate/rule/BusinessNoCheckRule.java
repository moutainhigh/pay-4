package com.pay.api.validate.rule;

import java.util.regex.Pattern;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

public class BusinessNoCheckRule extends MessageRule {

	static String pattern = "^[A-Za-z0-9]+$";

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String businessNo = request.getBizNo();

		boolean flag = Pattern.matches(pattern, businessNo);
		if (flag) {
			result.setBizNo(businessNo);
			return true;
		} else {
			result.setErrorCode(ErrorCode.BUSINESSNO_INVALID);
			result.setErrorMsg(ErrorCode.BUSINESSNO_INVALID_DESC);
			request.setResult(result);
			return flag;
		}
	}

}
