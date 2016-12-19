package com.pay.pe.service.payment.impl;

import org.springframework.util.Assert;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.pe.exception.ErrorCodeType;
import com.pay.pe.exception.PEBisnessRuntimeException;
import com.pay.pe.helper.OrgType;
import com.pay.pe.service.payment.AbstractAcctCodeGenerater;
import com.pay.pe.service.payment.AbstractPostingRule;
import com.pay.pe.service.payment.common.AcctCodeGeneraterParam;
import com.pay.util.StringUtil;

public class FxAcctCodeGenerater extends AbstractAcctCodeGenerater {

	public String generate(AcctCodeGeneraterParam param) {

		Assert.notNull(param);

		AbstractPostingRule postingRule = param.getPostingRule();
		String targetAccountCode = postingRule.getAccountCode();

		if (!StringUtil.isEmpty(targetAccountCode)) {
			return targetAccountCode;
		}

		String currencyNum = param.getCurrencyNum();
		Assert.notNull(param.getCurrencyNum());

		Integer orgType = param.getOrgTypeCode();

		Assert.notNull(orgType, "orgType must be not null");

		Integer memberType = param.getMemberType();

		// FX: 只在当partytypecode是机构的时候才有效
		String accountTypeCode = postingRule.getAcctAliasAcctTypeCode();

		if (OrgType.MEMBER.getValue() == orgType) {
			if (isMbrAcctSpecific(param)) {
				targetAccountCode = param.getFullMemberAccCode();
				// 如果是非指定会员帐户，个人会员
			} else if (MemberTypeEnum.INDIVIDUL.getCode() == memberType) {
				targetAccountCode = postingRule.getAcctAliasIndMbr()
						+ currencyNum + param.getMemberAcctCode();
			} else if (MemberTypeEnum.MERCHANT.getCode() == memberType) {
				targetAccountCode = postingRule.getAcctAliasBizMbr()
						+ currencyNum + param.getMemberAcctCode();
			}

		} else if (OrgType.BANK.getValue() == orgType) {
			targetAccountCode = accountTypeCode + param.getOrgCode()
					+ currencyNum;
		} else if (OrgType.INNER.getValue() == orgType) {
			targetAccountCode = accountTypeCode + currencyNum;
		} else {
			throw new PEBisnessRuntimeException(
					ErrorCodeType.GENERATEACCTCODE_IS_WRONG,
					"Invalid account type code or invalid account org type.");
		}
		return targetAccountCode;
	}

	@Override
	public String generateSpecAcct(String acctcode, String currencyNum) {
		return acctcode + currencyNum;
	}

}
