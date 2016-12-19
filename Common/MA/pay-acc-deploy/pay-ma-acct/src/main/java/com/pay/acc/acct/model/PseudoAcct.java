/**
 * 
 */
package com.pay.acc.acct.model;

/**
 * @author PengJiangbo
 *
 */
public class PseudoAcct extends Acct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 账户类型 */
	private Integer acctType ;
	/** 冻结资金 */
	private Long frozenAmount ;
	/** 基本账户和保证金账户冻结资金的总和 */
	private Long totalFrozenAmount ;
	
	/** 可用余额String形式 */
	private String balanceStr ;
	
	/** 冻结金额String形式 */
	private String frozenAmountStr ;
	
	/** 冻结资金总和String形式 */
	private String totalFrozenAmountStr ;
	
	/** 状态String形式 */
	private String statusStr ;
	
	public Integer getAcctType() {
		return acctType;
	}
	public void setAcctType(final Integer acctType) {
		this.acctType = acctType;
	}
	public Long getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(final Long frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public Long getTotalFrozenAmount() {
		return totalFrozenAmount;
	}
	public void setTotalFrozenAmount(final Long totalFrozenAmount) {
		this.totalFrozenAmount = totalFrozenAmount;
	}
	public String getBalanceStr() {
		return balanceStr;
	}
	public void setBalanceStr(final String balanceStr) {
		this.balanceStr = balanceStr;
	}
	public String getFrozenAmountStr() {
		return frozenAmountStr;
	}
	public void setFrozenAmountStr(final String frozenAmountStr) {
		this.frozenAmountStr = frozenAmountStr;
	}
	public String getTotalFrozenAmountStr() {
		return totalFrozenAmountStr;
	}
	public void setTotalFrozenAmountStr(final String totalFrozenAmountStr) {
		this.totalFrozenAmountStr = totalFrozenAmountStr;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(final String statusStr) {
		this.statusStr = statusStr;
	}
	
}
