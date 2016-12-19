package com.pay.acc.member.model;

public class WithdrawUnionBatchpayFee {
	
	/** 会员号 */
	private Long memberCode;
	/** 提现手续费 */
	private Long withdrawFee ; 
	/** 提现手续费币种 */
	private String withdrawFeeCurrency ;
	/** 批量出款手续费 */
	private Long batchpayFee ;
	/** 批量出款手续费币种  */
	private String batchpayFeeCurrency ;
	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the withdrawFee
	 */
	public Long getWithdrawFee() {
		return withdrawFee;
	}
	/**
	 * @param withdrawFee the withdrawFee to set
	 */
	public void setWithdrawFee(final Long withdrawFee) {
		this.withdrawFee = withdrawFee;
	}
	/**
	 * @return the withdrawFeeCurrency
	 */
	public String getWithdrawFeeCurrency() {
		return withdrawFeeCurrency;
	}
	/**
	 * @param withdrawFeeCurrency the withdrawFeeCurrency to set
	 */
	public void setWithdrawFeeCurrency(final String withdrawFeeCurrency) {
		this.withdrawFeeCurrency = withdrawFeeCurrency;
	}
	/**
	 * @return the batchpayFee
	 */
	public Long getBatchpayFee() {
		return batchpayFee;
	}
	/**
	 * @param batchpayFee the batchpayFee to set
	 */
	public void setBatchpayFee(final Long batchpayFee) {
		this.batchpayFee = batchpayFee;
	}
	/**
	 * @return the batchpayFeeCurrency
	 */
	public String getBatchpayFeeCurrency() {
		return batchpayFeeCurrency;
	}
	/**
	 * @param batchpayFeeCurrency the batchpayFeeCurrency to set
	 */
	public void setBatchpayFeeCurrency(final String batchpayFeeCurrency) {
		this.batchpayFeeCurrency = batchpayFeeCurrency;
	}
	
	
}
