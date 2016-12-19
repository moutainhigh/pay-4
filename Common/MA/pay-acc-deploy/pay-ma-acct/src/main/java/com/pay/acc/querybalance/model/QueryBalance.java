package com.pay.acc.querybalance.model;

import java.math.BigDecimal;

/**
 * @author jerry_jin
 * @version 
 * @data 2010-10-2
 * 账户余额明细
 */
public class QueryBalance {
	
	/**
	 * 时间
	 */
	private String balanceDate;
	/**
	 * 支付流水号
	 */
	private String  payNo;
	/**
	 * 交易类型
	 */
	private String fundTrace;

	/**
	 * 交易金额
	 */
	private BigDecimal amount;
	
	/**
	 * 余额
	 */
	private BigDecimal balance;
	
	/**
	 * 支出
	 */
	private BigDecimal pay;
	
	/**
	 * 收入
	 */
	private BigDecimal revenue;
	
	/**
	 * 收款方费用
	 */
	private BigDecimal payeeFee;
	
	/**
	 * 付款方费用
	 */
	private BigDecimal payerFee;
	
	private String payeeAccount;
	
	private String payerAccount;
	
	private Long dealCode;
	
	public String getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	
	public String getFundTrace() {
		return fundTrace;
	}
	public void setFundTrace(String fundTrace) {
		this.fundTrace = fundTrace;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getPay() {
		return pay;
	}
	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getPayeeFee() {
		return payeeFee;
	}
	public void setPayeeFee(BigDecimal payeeFee) {
		this.payeeFee = payeeFee;
	}
	public BigDecimal getPayerFee() {
		return payerFee;
	}
	public void setPayerFee(BigDecimal payerFee) {
		this.payerFee = payerFee;
	}
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public Long getDealCode() {
		return dealCode;
	}
	public void setDealCode(Long dealCode) {
		this.dealCode = dealCode;
	}
	
}
