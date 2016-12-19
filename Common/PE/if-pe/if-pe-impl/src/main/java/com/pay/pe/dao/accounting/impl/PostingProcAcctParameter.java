
package com.pay.pe.dao.accounting.impl;

import java.io.Serializable;


public final class PostingProcAcctParameter implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1286779357562200317L;
	
	/**
	 * 余额修改.
	 */
	private long balance;
	
	/**
	 * 借方余额.
	 */
	private long debitBalance;
	
	/**
	 * 贷方余额.
	 */
	private long creditBalance;
	
	/**
	 * 账号.
	 */
	private String accountCode;
	
	/**
	 * 余额是否可以为负.
	 */
	private boolean canBeNegativeBalance;

	/**
	 * @return Returns the accountCode.
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode The accountCode to set.
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return Returns the balance.
	 */
	public long getBalance() {
		return balance;
	}

	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(long balance) {
		this.balance = balance;
	}

	/**
	 * @return Returns the creditBalance.
	 */
	public long getCreditBalance() {
		return creditBalance;
	}

	/**
	 * @param creditBalance The creditBalance to set.
	 */
	public void setCreditBalance(long creditBalance) {
		this.creditBalance = creditBalance;
	}

	/**
	 * @return Returns the debitBalance.
	 */
	public long getDebitBalance() {
		return debitBalance;
	}

	/**
	 * @param debitBalance The debitBalance to set.
	 */
	public void setDebitBalance(long debitBalance) {
		this.debitBalance = debitBalance;
	}

	/**
	 * @return Returns the canBeNegativeBalance.
	 */
	public boolean isCanBeNegativeBalance() {
		return canBeNegativeBalance;
	}

	/**
	 * @param canBeNegativeBalance The canBeNegativeBalance to set.
	 */
	public void setCanBeNegativeBalance(boolean canBeNegativeBalance) {
		this.canBeNegativeBalance = canBeNegativeBalance;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("PostingProcAcctParameter ( ")
	        .append(super.toString()).append(TAB)
	        .append("balance = ").append(this.balance).append(TAB)
	        .append("debitBalance = ").append(this.debitBalance).append(TAB)
	        .append("creditBalance = ").append(this.creditBalance).append(TAB)
	        .append("accountCode = ").append(this.accountCode).append(TAB)
	        .append("canBeNegativeBalance = ").append(this.canBeNegativeBalance).append(TAB)
	        .append(" )");
	    
	    return retValue.toString();
	}
}
