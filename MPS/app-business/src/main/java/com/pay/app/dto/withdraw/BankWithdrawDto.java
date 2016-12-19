/**
 *  File: BankWithdrawDto.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-16   terry_ma     Create
 *
 */
package com.pay.app.dto.withdraw;

/**
 * 
 */
public class BankWithdrawDto {

	private String name;
	private String bankCode;
	private String bankName;
	private String bankAddress;
	private String cardNumberShow;
	private String cardNumber;
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardNumberShow() {
		return cardNumberShow;
	}
	public void setCardNumberShow(String cardNumberShow) {
		this.cardNumberShow = cardNumberShow;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}