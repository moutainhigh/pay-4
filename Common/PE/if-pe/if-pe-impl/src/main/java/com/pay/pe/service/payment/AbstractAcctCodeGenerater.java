package com.pay.pe.service.payment;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.pe.helper.OrgType;
import com.pay.pe.service.payment.common.AcctCodeGeneraterParam;

public abstract class AbstractAcctCodeGenerater {

	protected boolean isMbrAcctSpecific(AcctCodeGeneraterParam param) {
		return (param.getPostingRule().isMbrAcctSpecific());
	}

	protected boolean isPersonal(String partyTypeCode) {
		return MemberTypeEnum.INDIVIDUL.getCode() == Integer
				.parseInt(partyTypeCode);
	}

	protected boolean isUnit(String partyTypeCode) {
		return MemberTypeEnum.MERCHANT.getCode() == Integer
				.parseInt(partyTypeCode);
	}

	protected boolean isINNER(String partyTypeCode) {
		return OrgType.INNER.getValue() == Integer.parseInt(partyTypeCode);
	}

	protected boolean isBank(String partyTypeCode) {
		return OrgType.BANK.getValue() == Integer.parseInt(partyTypeCode);
	}

	public abstract String generate(AcctCodeGeneraterParam param);

	public abstract String generateSpecAcct(String acctcode, String currencyNum);

}
