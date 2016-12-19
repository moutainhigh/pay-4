/**
 *  File: AuditFlagCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 23, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.AuditFlag;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class AuditFlagCheckRule extends MessageRule {

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		String auditFlag = request.getAuditFlag();

		try {
			AuditFlag audit = AuditFlag
					.getAuditFlag(Integer.valueOf(auditFlag));
			if (null != audit) {
				return true;
			}
		} catch (Exception e) {
		}
		result.setErrorCode(ErrorCode.AUDITFLAG_INVALID);
		result.setErrorMsg(ErrorCode.AUDITFLAG_INVALID_DESC);
		request.setResult(result);
		return false;
	}

}
