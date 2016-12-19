package com.pay.acc.service.account.dto;

import java.io.Serializable;

public class BorrowBalanceDto implements Serializable{

	private static final long serialVersionUID = 8591006917257139087L;
	
	/**
	 * 借方发生总额
	 */
	private Long creditSumBalance;
	/**
	 * 贷方发生总额
	 */
	private Long debitSumBalance;
	private String acctCode;
	private Long memberCode;
	
	public BorrowBalanceDto(Long memberCode,String acctCode,Long creditSumBalance,Long debitSumBalance){
		this.memberCode=memberCode;
		this.acctCode=acctCode;
		this.creditSumBalance=creditSumBalance;
		this.debitSumBalance=debitSumBalance;
	}
	
	public Long getCreditSumBalance() {
		return creditSumBalance;
	}
	public void setCreditSumBalance(Long creditSumBalance) {
		this.creditSumBalance = creditSumBalance;
	}
	public Long getDebitSumBalance() {
		return debitSumBalance;
	}
	public void setDebitSumBalance(Long debitSumBalance) {
		this.debitSumBalance = debitSumBalance;
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
	
	

}
