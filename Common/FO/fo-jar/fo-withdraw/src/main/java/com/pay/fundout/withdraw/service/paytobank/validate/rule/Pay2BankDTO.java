/**
 *  File: Pay2BankDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.paytobank.validate.rule;

import java.math.BigDecimal;

/**
 * @author bill_peng
 * 
 */
public class Pay2BankDTO {

	/**
	 * 收款方用户名
	 */
	private String payeeUserId;
	/**
	 * 收款方姓名
	 */
	private String payeeName;
	/**
	 * 收款方银行代码
	 */
	private String payeeBankCode;
	
	/**
	 * 收款方银行名称
	 */
	private String payeeBankName;
	
	
	/**
	 * 收款方开户行所属省份
	 */
	private String payeeBankProvince;
	
	private String payeeBankProvinceName;
	
	/**
	 * 收款方开户行所属城市
	 */
	private String payeeBankCity;
	
	private String payeeBankCityName;
	
	/**
	 * 收款方开户行名称
	 */
	private String payeeOpeningBankName;
	/**
	 * 收款方银行账号
	 */
	private String payeeBankAccount;
	/**
	 * 收款方银行账号（重复输入）
	 */
	private String payeeRepeatBankAccount;
	
	/**
	 * 付款方账户类型
	 */
	private String payerAccountType;
	/**
	 * 付款方账户余额
	 */
	private BigDecimal payerAccountBalance;
	
	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;
	
	/**
	 * 付款金额
	 */
	private Long paymentAmountLong;
	
	/**
	 * 付款理由
	 */
	private String paymentReason;
	
	/**
	 * 手续费
	 */
	private BigDecimal handlingCharge; 
	
	/**
	 * 手续费
	 */
	private long handlingChargeLong; 
	
	/**
	 * 付款方式 1：先验证对方支付账户再付款；2：直接付款到对方银行账户
	 */
	private Integer paymentType;
	
	/**
	 * 是否付款方付手续费 0:收款方支付手续费；1:付款方支付手续费
	 */
	private Integer isPayerPaymentHandlingCharge = new Integer(0);
	
	/**
	 * 付款方式为先验证收款方支付账户再付款的限额
	 */
	private BigDecimal pay2BankWithAcctLimitAmount;
	/**
	 * 付款方式为先验证收款方支付账户再付款的限额
	 */
	private Long pay2BankWithAcctLimitAmountLong;
	
	/**
	 * 付款方式为直接付款到银行账户的限额
	 */
	private BigDecimal pay2BankWithoutAcctLimitAmount;
	
	/**
	 * 付款方式为直接付款到银行账户的限额
	 */
	private Long pay2BankWithoutAcctLimitAmountLong;
	
	
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
	private Integer limitTimes;
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
	private Long allowPaymentAmountLong;
	
	
	/**
	 * 支付密码
	 */
	private String paymentPwd;
	
	/**
	 * 付款步骤 1:选择付款方式；2:填写付款信息；3:确认付款信息 ；4:成功
	 */
	private int step;
	
	/**
	 * 交易号
	 */
	private Long dealId;
	/**
	 * 付款方会员账户
	 */
	private long memberCode;
	
	/**
	 * 付款方用户名
	 */
	private String payerUserId;
	
	/**
	 * 消息ID
	 */
	private String messageId;

	/**
	 * 实付金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 实付金额
	 */
	private Long payAmountLong;
	/**
	 * 实收金额
	 */
	private BigDecimal receiveAmount;
	
	/**
	 * 实收金额
	 */
	private Long receiveAmountLong;
	/**
	 * 收款方手机号码
	 */
	private String payeeMobile;
	/**
	 * 交易类型 0：个人；1：企业
	 */
	private Integer tradeType;
	
	
	
	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Long getPayAmountLong() {
		return payAmountLong;
	}

	public void setPayAmountLong(Long payAmountLong) {
		this.payAmountLong = payAmountLong;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Long getReceiveAmountLong() {
		return receiveAmountLong;
	}

	public void setReceiveAmountLong(Long receiveAmountLong) {
		this.receiveAmountLong = receiveAmountLong;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
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

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeBankAccount() {
		return payeeBankAccount;
	}

	public void setPayeeBankAccount(String payeeBankAccount) {
		this.payeeBankAccount = payeeBankAccount;
	}

	public String getPayeeRepeatBankAccount() {
		return payeeRepeatBankAccount;
	}

	public void setPayeeRepeatBankAccount(String payeeRepeatBankAccount) {
		this.payeeRepeatBankAccount = payeeRepeatBankAccount;
	}

	public String getPayerAccountType() {
		return payerAccountType;
	}

	public void setPayerAccountType(String payerAccountType) {
		this.payerAccountType = payerAccountType;
	}

	public BigDecimal getPayerAccountBalance() {
		return payerAccountBalance;
	}

	public void setPayerAccountBalance(BigDecimal payerAccountBalance) {
		this.payerAccountBalance = payerAccountBalance;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public BigDecimal getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getIsPayerPaymentHandlingCharge() {
		return isPayerPaymentHandlingCharge;
	}

	public void setIsPayerPaymentHandlingCharge(
			Integer isPayerPaymentHandlingCharge) {
		this.isPayerPaymentHandlingCharge = isPayerPaymentHandlingCharge;
	}

	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}

	public void setPayeeBankCityName(String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}

	public BigDecimal getPay2BankWithAcctLimitAmount() {
		return pay2BankWithAcctLimitAmount;
	}

	public void setPay2BankWithAcctLimitAmount(
			BigDecimal pay2BankWithAcctLimitAmount) {
		this.pay2BankWithAcctLimitAmount = pay2BankWithAcctLimitAmount;
	}

	public BigDecimal getPay2BankWithoutAcctLimitAmount() {
		return pay2BankWithoutAcctLimitAmount;
	}

	public void setPay2BankWithoutAcctLimitAmount(
			BigDecimal pay2BankWithoutAcctLimitAmount) {
		this.pay2BankWithoutAcctLimitAmount = pay2BankWithoutAcctLimitAmount;
	}

	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}


	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}

	public void setPayeeBankProvinceName(String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}

	public String getPaymentPwd() {
		return paymentPwd;
	}

	public void setPaymentPwd(String paymentPwd) {
		this.paymentPwd = paymentPwd;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getPaymentAmountLong() {
		return paymentAmountLong;
	}

	public void setPaymentAmountLong(Long paymentAmountLong) {
		this.paymentAmountLong = paymentAmountLong;
	}

	public long getHandlingChargeLong() {
		return handlingChargeLong;
	}

	public void setHandlingChargeLong(long handlingChargeLong) {
		this.handlingChargeLong = handlingChargeLong;
	}

	public Long getPay2BankWithAcctLimitAmountLong() {
		return pay2BankWithAcctLimitAmountLong;
	}

	public void setPay2BankWithAcctLimitAmountLong(
			Long pay2BankWithAcctLimitAmountLong) {
		this.pay2BankWithAcctLimitAmountLong = pay2BankWithAcctLimitAmountLong;
	}

	public Long getPay2BankWithoutAcctLimitAmountLong() {
		return pay2BankWithoutAcctLimitAmountLong;
	}

	public void setPay2BankWithoutAcctLimitAmountLong(
			Long pay2BankWithoutAcctLimitAmountLong) {
		this.pay2BankWithoutAcctLimitAmountLong = pay2BankWithoutAcctLimitAmountLong;
	}

	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
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
