/**
 *  <p>File: OrderInfo.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.dto;

import java.io.Serializable;

public class OrderInfo implements Serializable {

	private static final long serialVersionUID = 7575966649306667586L;
	/**
	 * 订单交易号
	 */
	private String dealId;
	/**
	 * 交易日期
	 */
	private String orderTime;
	/**
	 * 交易类型
	 */
	private Integer dealType;
	/**
	 * 交易收支
	 */
	private Integer crdr;
	/**
	 * 交易状态
	 */
	private Integer dealStatus;
	/**
	 * 交易金额
	 */
	private Long dealAmount;
	/**
	 * 交易费用
	 */
	private Long dealFee;

	private Long realpayAmount;// 实际付款金额

	private Long realoutAmount;// 实际出款金额
	
	private Long balance; //账户余额
	
	private String payerAcctType;//账户类型
	
	private String currencyCode;//币种
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(String payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	private String  balanceStr;
	
	public String getBalanceStr() {
		return balanceStr;
	}

	public void setBalanceStr(String balanceStr) {
		this.balanceStr = balanceStr;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public Long getRealoutAmount() {
		return realoutAmount;
	}

	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public Integer getCrdr() {
		return crdr;
	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Long getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(Long dealAmount) {
		this.dealAmount = dealAmount;
	}

	public Long getDealFee() {
		return dealFee;
	}

	public void setDealFee(Long dealFee) {
		this.dealFee = dealFee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
