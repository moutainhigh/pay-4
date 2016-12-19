package com.pay.acc.service.account.dto;

import java.io.Serializable;

public class BalancesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String acctCode;

	private Long memberCode;

	private String acctName;

	private Long memberAcctCode;

	private Integer acctType;
	
	private String currencyCode;

	private Long balance = 0L;

	private Long frozeAmount = 0L;

	private Long withdrawBalance = 0L;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getFrozeAmount() {
		return frozeAmount;
	}

	public void setFrozeAmount(Long frozeAmount) {
		this.frozeAmount = frozeAmount;
	}

	public Long getWithdrawBalance() {
		return withdrawBalance;
	}

	public void setWithdrawBalance(Long withdrawBalance) {
		this.withdrawBalance = withdrawBalance;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getMemberAcctCode() {
		return memberAcctCode;
	}

	public void setMemberAcctCode(Long memberAcctCode) {
		this.memberAcctCode = memberAcctCode;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "BalancesDto [acctCode=" + acctCode + ", memberCode="
				+ memberCode + ", acctName=" + acctName + ", memberAcctCode="
				+ memberAcctCode + ", acctType=" + acctType + ", currencyCode="
				+ currencyCode + ", balance=" + balance + ", frozeAmount="
				+ frozeAmount + ", withdrawBalance=" + withdrawBalance
				+ ", status=" + status + "]";
	}
	
}