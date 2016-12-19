/**
 *  File: PaymentResponse.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fo.order.validate;

/**
 * 
 */
public class PaymentResponse {

	/**
	 * 目的银行
	 */
	private String payeeBankCode;
	
	/**
	 * 出款银行
	 */
	private String bankChannelCode;
	
	/**
	 * 收款行所在省份代码
	 */
	private String payeeBankProvince;
	
	/**
	 * 收款行所在城市代码
	 */
	private String payeeBankCity;
	
	/**
	 * 银行联行号
	 */
	private String bankNumber;

	/**
	 * 验证错误信息
	 */
	private String errorMsg;
	
	private Long paymentAmount;


	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public String getPayeeBankProvince() {
		return payeeBankProvince;
	}

	public void setPayeeBankProvince(String payeeBankProvince) {
		this.payeeBankProvince = payeeBankProvince;
	}

	public String getPayeeBankCity() {
		return payeeBankCity;
	}

	public void setPayeeBankCity(String payeeBankCity) {
		this.payeeBankCity = payeeBankCity;
	}

	public String getBankChannelCode() {
		return bankChannelCode;
	}

	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public String toString() {
		return "PaymentResponse [payeeBankCode=" + payeeBankCode
				+ ", bankChannelCode=" + bankChannelCode
				+ ", payeeBankProvince=" + payeeBankProvince
				+ ", payeeBankCity=" + payeeBankCity + ", bankNumber="
				+ bankNumber + ", errorMsg=" + errorMsg + ", paymentAmount="
				+ paymentAmount + "]";
	}
	
}
