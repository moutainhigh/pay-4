
package com.pay.pe.dao.accounting.impl;

import java.io.Serializable;
import java.util.Date;


public final class PostingProcEntryParameter implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8078537719697428929L;
	
	/**
	 * 账号.
	 */
	private String accountCode;
	
	/**
	 * 借贷标识.
	 */
	private int crdr;
	
	/**
	 * 金额.
	 */
	private long amount;
	
	/**
	 * 备注.
	 */
	private String memo;
	
	/**
	 * 交易号.
	 */
	private String dealId;
	
	/**
	 * 支付服务代码.
	 */
	private int paymentServiceCode;
	
	/**
	 * 分录状态.
	 */
	private int status;
	
	/**
	 * 分录序列号.
	 */
	private int entryCode;
	
	/**
	 * 记账币种
	 */
	private String currencyCode;
	
	/**
	 * 记帐汇率
	 */
	private Long exchangeRate;
	
	/**
	 * 实际交易时间，如果为空则默认为dealbegindate
	 */
	private Date transactiondate;

	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

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
	 * @return Returns the amount.
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the crdr.
	 */
	public int getCrdr() {
		return crdr;
	}

	/**
	 * @param crdr The crdr to set.
	 */
	public void setCrdr(int crdr) {
		this.crdr = crdr;
	}

	/**
	 * @return Returns the dealId.
	 */
	public String getDealId() {
		return dealId;
	}

	/**
	 * @param dealId The dealId to set.
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	/**
	 * @return Returns the entryCode.
	 */
	public int getEntryCode() {
		return entryCode;
	}

	/**
	 * @param entryCode The entryCode to set.
	 */
	public void setEntryCode(int entryCode) {
		this.entryCode = entryCode;
	}

	/**
	 * @return Returns the memo.
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo The memo to set.
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return Returns the paymentServiceCode.
	 */
	public int getPaymentServiceCode() {
		return paymentServiceCode;
	}

	/**
	 * @param paymentServiceCode The paymentServiceCode to set.
	 */
	public void setPaymentServiceCode(int paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}

	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
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
	    
	    retValue.append("PostingProcEntryParameter ( ")
	        .append(super.toString()).append(TAB)
	        .append("accountCode = ").append(this.accountCode).append(TAB)
	        .append("crdr = ").append(this.crdr).append(TAB)
	        .append("amount = ").append(this.amount).append(TAB)
	        .append("memo = ").append(this.memo).append(TAB)
	        .append("dealId = ").append(this.dealId).append(TAB)
	        .append("paymentServiceCode = ").append(this.paymentServiceCode).append(TAB)
	        .append("status = ").append(this.status).append(TAB)
	        .append("entryCode = ").append(this.entryCode).append(TAB)
	        .append("currencyCode = ").append(this.currencyCode).append(TAB)
	        .append("exchangeRate = ").append(this.exchangeRate).append(TAB)
	        .append("transactiondate = ").append(this.transactiondate).append(TAB)
	        .append(" )");
	    
	    return retValue.toString();
	}
}
