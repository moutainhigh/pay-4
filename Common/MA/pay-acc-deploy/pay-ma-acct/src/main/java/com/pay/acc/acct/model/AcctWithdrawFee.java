/**
 * 
 */
package com.pay.acc.acct.model;

/**
 * @author PengJiangbo
 *
 */
public class AcctWithdrawFee {
	
	/** 提现账户 */
	private String acctCode ;
	/** 账户提现手续费币种 */
	private String acctWithDrawCurrencyCode ;
	/** 账户提现手续费金额 */
	private Long acctWithDrawFee ;
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(final String acctCode) {
		this.acctCode = acctCode;
	}
	public String getAcctWithDrawCurrencyCode() {
		return acctWithDrawCurrencyCode;
	}
	public void setAcctWithDrawCurrencyCode(final String acctWithDrawCurrencyCode) {
		this.acctWithDrawCurrencyCode = acctWithDrawCurrencyCode;
	}
	public Long getAcctWithDrawFee() {
		return acctWithDrawFee;
	}
	public void setAcctWithDrawFee(final Long acctWithDrawFee) {
		this.acctWithDrawFee = acctWithDrawFee;
	}
	@Override
	public String toString() {
		return "AcctWithdrawFee [acctCode=" + acctCode
				+ ", acctWithDrawCurrencyCode=" + acctWithDrawCurrencyCode
				+ ", acctWithDrawFee=" + acctWithDrawFee + "]";
	}
	
}
