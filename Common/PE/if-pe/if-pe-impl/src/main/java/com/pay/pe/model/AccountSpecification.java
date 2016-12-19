package com.pay.pe.model;

import com.pay.inf.model.Model;



public class AccountSpecification implements Model{

	private String acctCode;
	private Integer acctContent;
	private Integer acctLevel;
	private String acctName;
	private Integer acctType;
	private Integer balanceBy;
	private Integer childBeorg;
	private Integer childOrgType;
	private String currencyCode;
	private String describe;
	private Integer isMemberAcct;
	private String nonCurrencyUnit;
	private Long orgCode;
	private Integer orgType;
	private Integer precision;
	private Integer referable;
	private String summarizeTo;
	private Integer negativeBalance;
	private Integer acctDiaryUpdateMethod;
	private Integer sumAcctUpdateMethod;
	private Integer status;

	public AccountSpecification() {
		super();
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Integer getAcctContent() {
		return acctContent;
	}

	public void setAcctContent(Integer acctContent) {
		this.acctContent = acctContent;
	}

	public Integer getAcctLevel() {
		return acctLevel;
	}

	public void setAcctLevel(Integer acctLevel) {
		this.acctLevel = acctLevel;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Integer getBalanceBy() {
		return balanceBy;
	}

	public void setBalanceBy(Integer balanceBy) {
		this.balanceBy = balanceBy;
	}

	public Integer getChildBeorg() {
		return childBeorg;
	}

	public void setChildBeorg(Integer childBeorg) {
		this.childBeorg = childBeorg;
	}

	public Integer getChildOrgType() {
		return childOrgType;
	}

	public void setChildOrgType(Integer childOrgType) {
		this.childOrgType = childOrgType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getIsMemberAcct() {
		return isMemberAcct;
	}

	public void setIsMemberAcct(Integer isMemberAcct) {
		this.isMemberAcct = isMemberAcct;
	}

	public String getNonCurrencyUnit() {
		return nonCurrencyUnit;
	}

	public void setNonCurrencyUnit(String nonCurrencyUnit) {
		this.nonCurrencyUnit = nonCurrencyUnit;
	}

	public Long getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(Long orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getReferable() {
		return referable;
	}

	public void setReferable(Integer referable) {
		this.referable = referable;
	}

	public String getSummarizeTo() {
		return summarizeTo;
	}

	public void setSummarizeTo(String summarizeTo) {
		this.summarizeTo = summarizeTo;
	}

	public Integer getNegativeBalance() {
		return negativeBalance;
	}

	public void setNegativeBalance(Integer negativeBalance) {
		this.negativeBalance = negativeBalance;
	}

	public Integer getAcctDiaryUpdateMethod() {
		return acctDiaryUpdateMethod;
	}

	public void setAcctDiaryUpdateMethod(Integer acctDiaryUpdateMethod) {
		this.acctDiaryUpdateMethod = acctDiaryUpdateMethod;
	}

	public Integer getSumAcctUpdateMethod() {
		return sumAcctUpdateMethod;
	}

	public void setSumAcctUpdateMethod(Integer sumAcctUpdateMethod) {
		this.sumAcctUpdateMethod = sumAcctUpdateMethod;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
