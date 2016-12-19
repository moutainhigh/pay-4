/**
 *  File: BspWithdrawCommand.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 28, 2011   terry ma      create
 */
package com.pay.controller.fo.bsp;


public class BspWithdrawCommand {

	private Long withdrawBankSequenceId;
	private String withdrawAmount;
	private String bankName;
	private String merchantName;
	public Long getWithdrawBankSequenceId() {
		return withdrawBankSequenceId;
	}
	public void setWithdrawBankSequenceId(Long withdrawBankSequenceId) {
		this.withdrawBankSequenceId = withdrawBankSequenceId;
	}

	public String getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
