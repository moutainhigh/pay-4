/**
 *  <p>File: AccountingFee.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.pe.dto;

import java.io.Serializable;

public class AccountingFeeRes implements Serializable {
	
	/**
	 * 记账金额
	 */
	private Long amount;

	/**
	 * 付款方手续费
	 */
	private Long payerFee;
	/**
	 * 收款方手续费
	 */
	private Long payeeFee;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}
	
}
