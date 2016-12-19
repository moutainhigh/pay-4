package com.pay.base.model;

import java.math.BigDecimal;

/**
 * @author jerry_jin
 * @version 
 * @data 2010-10-2
 * 账户余额明细
 */
public class QueryCorpBalance {
	
	/**
	 * 时间
	 */
	private String balanceDate;
	/**
	 * 支付流水号
	 */
	private String  payNo;
	/**
	 * 商户订单号
	 */
	private String  merchantOrderId;
	/**
	 * 备注
	 */
	private String  remark;
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
	
	/**
	 * 收入币种
	 */
	private String revenueCode;
	/**
	 * 支出币种
	 */
	private String payCode;
	
	//增加余额币种 PengJiangbo 2016.05.12
	private String balanceCurCode ;

	
	
	
	public String getRevenueCode() {
		return revenueCode;
	}
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

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
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the balanceCurCode
	 */
	public String getBalanceCurCode() {
		return balanceCurCode;
	}
	public void setBalanceCurCode(String balanceCurCode) {
		this.balanceCurCode = balanceCurCode;
	}
	
	
	
}
