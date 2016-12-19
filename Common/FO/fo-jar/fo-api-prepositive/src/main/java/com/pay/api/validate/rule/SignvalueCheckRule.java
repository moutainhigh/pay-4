/**
 *  File: SignvalueCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.SignType;
import com.pay.api.model.MerchantConfigure;
import com.pay.api.service.ISecurityProvider;
import com.pay.api.util.ParameterXmlParserUtil;
import com.pay.inf.rule.MessageRule;
import com.pay.util.MD5Util;

/**
 * 
 */
public class SignvalueCheckRule extends MessageRule {

	private ISecurityProvider securityProvider;
	static String regex = "/[(^\\s+)(\\s+$)]/g";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String signValue = request.getSignvalue();
		String requestXml = request.getXml();
		String signType = request.getSignType();
		MerchantConfigure configure = result.getConfigure();

		String srcData = ParameterXmlParserUtil.getNodeText(requestXml,
				"REQUEST_BODY");

		srcData = srcData.replaceAll("\\s*", "");

		boolean signFlag = false;

		if (SignType.MD5.getValue().equals(signType)) {
			String mac = MD5Util.md5Hex(srcData + configure.getPublicKey());
			signFlag = mac.equalsIgnoreCase(signValue);
		} else if (SignType.RSA.getValue().equals(signType)) {
			signFlag = securityProvider.verifySignature(srcData,
					configure.getPublicKey(), signValue);
		}

		if (!signFlag) {
			result.setErrorCode(ErrorCode.SIGN_INVALID);
			result.setErrorMsg(ErrorCode.SIGN_INVALID_DESC);
			request.setResult(result);
		}
		return signFlag;
	}

	public void setSecurityProvider(ISecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}

}
