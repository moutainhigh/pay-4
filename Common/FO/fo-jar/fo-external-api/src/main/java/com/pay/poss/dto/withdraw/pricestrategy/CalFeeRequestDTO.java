/**
 *  File: CalFeeRequestDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-27      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.pricestrategy;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 算费对象
 * @author zliner
 *
 */
public class CalFeeRequestDTO implements Serializable {
	/**
	 * 是否已经算费
	 */
	 private boolean hasCaculatedPrice;
	 private Long payeeFee;
	 /**
	  * 手续费
	  */
	 private Long payerFee;
	//序号
	private static final long serialVersionUID = -7412051985716234118L;
	//算费或记账交易号
	private String orderId;
	//算费金额或记账金额   
	private Long orderAmount;
	//付款方账号
	private String submitAcctCode;
	//算费金额或记账金额  (算费时必须设置)
	private Long amount;
	//财务指定的记账规则表中定制的支付服务组编号             (算费时必须设置)
	private String paymentServicePkgCode;
	//财务指定的记账规则表中定制的dealCode    (算费时必须设置)
	private Integer dealCode;
	//财务指定的记账规则表中定制的orderCode   (算费时必须设置)
	private Integer orderCode;
	//支付方式,1为直接到帐,默认用1            (算费时必须设置)
	private Integer payMethod;
	//终端类型标识从web还是wap还是别的渠道提交
	private int terminalType = 1;
	//提交日期
	private Date requestDate;
	//付款方的memberCode
	private String payer;
	//付款方Member Account Code,对应到t_acct表中的acct_code字段 
	private String payerMemberAcctCode;
	//账户类型 ,对应到t_acct表中的acct_type_id字段
	private String payerAcctType;
	//付款方机构类型代码       会员账户的时候无需传递该数据
	private String payerOrgType;
	//支付用户的机构代码       会员账户的时候无需传递该数据
	private String payerOrgCode;
	//付款方FullMember Account Code. 包含科目号的acctCode
	private String payerFullMemberAcctCode;
	//付款方的支付服务等级 
	private Integer payerServiceLevel = -1;
	//币种
	private String payerCurrencyCode;
	//收款用户memberCode
	private String payee;
	//收款用户的机构代码
	private String payeeOrgCode;
	//收款方机构类型代码
	private String payeeOrgType;
	//收款方Member Account Code
	private String payeeMemberAcctCode;
	//收款方账户类型
	private String payeeAcctType;
	//收款方FullMember Account Code. 包含科目号
	private String payeeFullMemberAcctCode;
	//收款方的支付服务等级 
	private Integer payeeServiceLevel = -1;
	//付款方币种
	private String payeeCurrencyCode;
	//兑换率
	private Long exchangeRate;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getPaymentServicePkgCode() {
		return paymentServicePkgCode;
	}

	public void setPaymentServicePkgCode(String paymentServicePkgCode) {
		this.paymentServicePkgCode = paymentServicePkgCode;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayerOrgCode() {
		return payerOrgCode;
	}

	public void setPayerOrgCode(String payerOrgCode) {
		this.payerOrgCode = payerOrgCode;
	}

	public String getSubmitAcctCode() {
		return submitAcctCode;
	}

	public void setSubmitAcctCode(String submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}

	public String getPayerMemberAcctCode() {
		return payerMemberAcctCode;
	}

	public void setPayerMemberAcctCode(String payerMemberAcctCode) {
		this.payerMemberAcctCode = payerMemberAcctCode;
	}

	public String getPayerFullMemberAcctCode() {
		return payerFullMemberAcctCode;
	}

	public void setPayerFullMemberAcctCode(String payerFullMemberAcctCode) {
		this.payerFullMemberAcctCode = payerFullMemberAcctCode;
	}

	public Integer getPayerServiceLevel() {
		return payerServiceLevel;
	}

	public void setPayerServiceLevel(Integer payerServiceLevel) {
		this.payerServiceLevel = payerServiceLevel;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayeeOrgCode() {
		return payeeOrgCode;
	}

	public void setPayeeOrgCode(String payeeOrgCode) {
		this.payeeOrgCode = payeeOrgCode;
	}

	public Integer getPayeeServiceLevel() {
		return payeeServiceLevel;
	}

	public void setPayeeServiceLevel(Integer payeeServiceLevel) {
		this.payeeServiceLevel = payeeServiceLevel;
	}

	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}

	public String getPayerOrgType() {
		return payerOrgType;
	}

	public void setPayerOrgType(String payerOrgType) {
		this.payerOrgType = payerOrgType;
	}

	public String getPayeeOrgType() {
		return payeeOrgType;
	}

	public void setPayeeOrgType(String payeeOrgType) {
		this.payeeOrgType = payeeOrgType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(String payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(String payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayeeMemberAcctCode() {
		return payeeMemberAcctCode;
	}

	public void setPayeeMemberAcctCode(String payeeMemberAcctCode) {
		this.payeeMemberAcctCode = payeeMemberAcctCode;
	}

	public String getPayeeFullMemberAcctCode() {
		return payeeFullMemberAcctCode;
	}

	public void setPayeeFullMemberAcctCode(String payeeFullMemberAcctCode) {
		this.payeeFullMemberAcctCode = payeeFullMemberAcctCode;
	}

	public boolean isHasCaculatedPrice() {
		return hasCaculatedPrice;
	}

	public void setHasCaculatedPrice(boolean hasCaculatedPrice) {
		this.hasCaculatedPrice = hasCaculatedPrice;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}
	
	public String toString(){
		if(this == null) return "";
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
