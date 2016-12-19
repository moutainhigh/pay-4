package com.pay.acc.deal.dto;

import java.io.Serializable;

public class BanlanceDealTempDto implements Serializable {

	private static final long serialVersionUID = -8761210360988966267L;
	/**
	 * 付款方FullMember Account Code. 包含科目号
	 */
	private String payerFullMemberAcctCode;
	/**
	 * 收款方FullMember Account Code. 包含科目号
	 */
	private String payeeFullMemberAcctCode;
	
	private Long amount;
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getPayerFullMemberAcctCode() {
		return payerFullMemberAcctCode;
	}
	public void setPayerFullMemberAcctCode(String payerFullMemberAcctCode) {
		this.payerFullMemberAcctCode = payerFullMemberAcctCode;
	}
	public String getPayeeFullMemberAcctCode() {
		return payeeFullMemberAcctCode;
	}
	
	public void setPayeeFullMemberAcctCode(String payeeFullMemberAcctCode) {
		this.payeeFullMemberAcctCode = payeeFullMemberAcctCode;
	}
	
	
	
}
