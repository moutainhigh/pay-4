package com.pay.pe.service.payment.impl;

import com.pay.pe.exception.ErrorCodeType;
import com.pay.pe.exception.PEBisnessRuntimeException;
import com.pay.pe.helper.OrgType;
import com.pay.pe.service.payment.AbstractAcctCodeGenerater;
import com.pay.pe.service.payment.AbstractPostingRule;
import com.pay.pe.service.payment.common.AcctCodeGeneraterParam;
import com.pay.util.StringUtil;

public class DefaultAcctCodeGenerater extends AbstractAcctCodeGenerater {

	@Override
	public String generate(AcctCodeGeneraterParam param) {

		AbstractPostingRule postingRule = param.getPostingRule();
		String targetAccountCode = postingRule.getAccountCode();

		if (!StringUtil.isEmpty(targetAccountCode)) {
			return targetAccountCode.trim();
		}

		Integer orgType = param.getOrgTypeCode();
		String accountTypeCode = postingRule.getAcctAliasAcctTypeCode();

		// 如果指定会员帐户，使用full member account code
		if (OrgType.MEMBER.getValue() == orgType) {
			if (isMbrAcctSpecific(param)) {
				targetAccountCode = param.getFullMemberAccCode();
			} else {
				// targetAccountCode =
			}

		} else if (OrgType.BANK.getValue() == orgType) {
			targetAccountCode = accountTypeCode + param.getOrgCode();
		} else if (OrgType.INNER.getValue() == orgType) {
			targetAccountCode = postingRule.getAccountCode();
		} else {
			throw new PEBisnessRuntimeException(
					ErrorCodeType.GENERATEACCTCODE_IS_WRONG,
					"Invalid account type code or invalid account org type.");
		}
		return targetAccountCode;
	}

	@Override
	public String generateSpecAcct(String acctcode, String currencyNum) {
		// 支付平台专用
		return acctcode;
	}

}
