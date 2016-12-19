package com.pay.acc.service.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class FreezeBalanceDto implements Serializable{
	
	private static final long serialVersionUID = 3915738885467518451L;
	/**
	 * 请求流水号 唯一
	 */
	private String transOrderSeq;
	
	/**
	 * 需要被冻结或解冻的会员号
	 */
	private Long memberCode;
	
	/**
	 * 需要被冻结或解冻的账户号
	 */
	private String acctCode;
	
	/**
	 * 人民币(10)
	 */
	private int acctType;
	
	/**
	 * 需要冻结或解冻的金额
	 */
	private BigDecimal amount;

	public String getTransOrderSeq() {
		return transOrderSeq;
	}

	public void setTransOrderSeq(String transOrderSeq) {
		this.transOrderSeq = transOrderSeq;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public int getAcctType() {
		return acctType;
	}

	public void setAcctType(int acctType) {
		this.acctType = acctType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
}
