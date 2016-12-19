package com.pay.pe.service.payment.common;

import com.pay.pe.service.payment.AbstractPostingRule;

public class AcctCodeGeneraterParam {

	private AbstractPostingRule postingRule;
	private Integer orgTypeCode;
	private String fullMemberAccCode;
	private String memberAcctCode;
	private String memberCode;
	private String orgCode;
	private String currencyNum;
	private Integer memberType;

	public String getCurrencyNum() {
		return currencyNum;
	}

	public void setCurrencyNum(String currencyNum) {
		this.currencyNum = currencyNum;
	}

	public AbstractPostingRule getPostingRule() {
		return postingRule;
	}

	public void setPostingRule(AbstractPostingRule postingRule) {
		this.postingRule = postingRule;
	}

	public Integer getOrgTypeCode() {
		return orgTypeCode;
	}

	public void setOrgTypeCode(Integer orgTypeCode) {
		this.orgTypeCode = orgTypeCode;
	}

	public String getFullMemberAccCode() {
		return fullMemberAccCode;
	}

	public void setFullMemberAccCode(String fullMemberAccCode) {
		this.fullMemberAccCode = fullMemberAccCode;
	}

	public String getMemberAcctCode() {
		return memberAcctCode;
	}

	public void setMemberAcctCode(String memberAcctCode) {
		this.memberAcctCode = memberAcctCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

}
