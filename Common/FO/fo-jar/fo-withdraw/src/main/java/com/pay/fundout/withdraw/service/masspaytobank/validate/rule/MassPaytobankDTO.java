/**
 *  File: MassPaytobankDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-11      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.validate.rule;

/**
 * @author bill_peng
 *
 */
public class MassPaytobankDTO {
	
	private Long payerMemberCode;
	
	private Integer payerAccountType;
	
	private Long paymentAmount;
	
	private Integer validateNum;
	
	private Integer isPayerPayFee;
	
	private Long fee;
	
	
	/**
	 * 每日限额
	 */
	private Long dayLimitAmountLong;
	/**
	 * 每月限额
	 */
	private Long monthLimitAmountLong;
	/**
	 * 剩余付款次数
	 */
	private Integer limitTimes = 0;
	/**
	 * 当日付款金额
	 */
	private Long currentDayAmountLong;
	/**
	 * 当月付款金额
	 */
	private Long currentMonthAmountLong;
	
	/**
	 * 允许付款金额
	 */
	private Long allowPaymentAmountLong = 0L;
	
	
	private String messageId;
	
	/**
	 * 当前操作 1:上传文件 2:确认付款 3:复核
	 */
	private int step;

	public Long getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}
	
	

	public Integer getPayerAccountType() {
		return payerAccountType;
	}

	public void setPayerAccountType(Integer payerAccountType) {
		this.payerAccountType = payerAccountType;
	}

	

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}


	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Integer getValidateNum() {
		return validateNum;
	}

	public void setValidateNum(Integer validateNum) {
		this.validateNum = validateNum;
	}

	public Long getDayLimitAmountLong() {
		return dayLimitAmountLong;
	}

	public void setDayLimitAmountLong(Long dayLimitAmountLong) {
		this.dayLimitAmountLong = dayLimitAmountLong;
	}

	public Long getMonthLimitAmountLong() {
		return monthLimitAmountLong;
	}

	public void setMonthLimitAmountLong(Long monthLimitAmountLong) {
		this.monthLimitAmountLong = monthLimitAmountLong;
	}

	public Integer getLimitTimes() {
		return limitTimes;
	}

	public void setLimitTimes(Integer limitTimes) {
		this.limitTimes = limitTimes;
	}

	public Long getCurrentDayAmountLong() {
		return currentDayAmountLong;
	}

	public void setCurrentDayAmountLong(Long currentDayAmountLong) {
		this.currentDayAmountLong = currentDayAmountLong;
	}

	public Long getCurrentMonthAmountLong() {
		return currentMonthAmountLong;
	}

	public void setCurrentMonthAmountLong(Long currentMonthAmountLong) {
		this.currentMonthAmountLong = currentMonthAmountLong;
	}

	public Long getAllowPaymentAmountLong() {
		return allowPaymentAmountLong;
	}

	public void setAllowPaymentAmountLong(Long allowPaymentAmountLong) {
		this.allowPaymentAmountLong = allowPaymentAmountLong;
	}
	
	

}
