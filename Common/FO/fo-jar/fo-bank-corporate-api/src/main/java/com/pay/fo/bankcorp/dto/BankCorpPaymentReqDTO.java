package com.pay.fo.bankcorp.dto;

import java.io.Serializable;
import java.util.Date;

public class BankCorpPaymentReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4324358286849641803L;
	
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 订单子类型
	 */
	private String orderSmallType;
	
	/**
	 * 实际出款金额
	 */
	private Long realoutAmount;
	
	/**
	 * 收款方名称
	 */
	private String payeeName;
	/**
	 * 收款方银行账号
	 */
	private String payeeBankAcctCode;
	/**
	 * 收款方银行账号类型
	 */
	private Integer payeeBankAcctType;
	/**
	 * 收款银行代码
	 */
	private String payeeBankCode;
	/**
	 * 收款银行名称
	 */
	private String payeeBankName;
	/**
	 * 收款方开户行名称
	 */
	private String payeeOpeningBankName;
	/**
	 * 收款行所在省份代码
	 */
	private String payeeBankProvince;
	/**
	 * 收款行所在省份名称
	 */
	private String payeeBankProvinceName;
	/**
	 * 收款行所在城市代码
	 */
	private String payeeBankCity;
	/**
	 * 收款行所在城市名称
	 */
	private String payeeBankCityName;
	/**
	 * 付款理由
	 */
	private String paymentReason;
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 交易别名
	 */
	private String tradeAlias;
	/**
	 * 交易类型
	 */
	private Integer tradeType;
	/**
	 * 银行订单号
	 */
	private String bankOrderId;
	/**
	 * 外部订单号
	 */
	private String foreignOrderId;
	/**
	 * 出款行代码
	 */
	private String fundoutBankCode;
	
	/**
	 * 出款方式
	 */
	private Integer fundoutMode;
	
	//出款渠道编号
	private String channelCode;
	
	/**
	 * 交易代码
	 */
	private String transCode; 
	
	/**
	 * 出款行名称
	 */
	private String fundoutBankName;
	
	
	
	/**
	 * 付款方银行账号（银行企业账号）
	 */
	private String payerBankAcc;

	/**
	 * 付款方银行账号名称(银行企业账号名称)
	 */
	private String payerBankAccName;
	
	
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderSmallType() {
		return orderSmallType;
	}

	public void setOrderSmallType(String orderSmallType) {
		this.orderSmallType = orderSmallType;
	}

	public Long getRealoutAmount() {
		return realoutAmount;
	}

	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}

	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	public Integer getPayeeBankAcctType() {
		return payeeBankAcctType;
	}

	public void setPayeeBankAcctType(Integer payeeBankAcctType) {
		this.payeeBankAcctType = payeeBankAcctType;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}

	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	public String getPayeeBankProvince() {
		return payeeBankProvince;
	}

	public void setPayeeBankProvince(String payeeBankProvince) {
		this.payeeBankProvince = payeeBankProvince;
	}

	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}

	public void setPayeeBankProvinceName(String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}

	public String getPayeeBankCity() {
		return payeeBankCity;
	}

	public void setPayeeBankCity(String payeeBankCity) {
		this.payeeBankCity = payeeBankCity;
	}

	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}

	public void setPayeeBankCityName(String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}

	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTradeAlias() {
		return tradeAlias;
	}

	public void setTradeAlias(String tradeAlias) {
		this.tradeAlias = tradeAlias;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

	public String getForeignOrderId() {
		return foreignOrderId;
	}

	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	public String getFundoutBankCode() {
		return fundoutBankCode;
	}

	public void setFundoutBankCode(String fundoutBankCode) {
		this.fundoutBankCode = fundoutBankCode;
	}

	public String getFundoutBankName() {
		return fundoutBankName;
	}

	public void setFundoutBankName(String fundoutBankName) {
		this.fundoutBankName = fundoutBankName;
	}

	public Integer getFundoutMode() {
		return fundoutMode;
	}

	public void setFundoutMode(Integer fundoutMode) {
		this.fundoutMode = fundoutMode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getPayerBankAcc() {
		return payerBankAcc;
	}

	public void setPayerBankAcc(String payerBankAcc) {
		this.payerBankAcc = payerBankAcc;
	}

	public String getPayerBankAccName() {
		return payerBankAccName;
	}

	public void setPayerBankAccName(String payerBankAccName) {
		this.payerBankAccName = payerBankAccName;
	}
	
}
