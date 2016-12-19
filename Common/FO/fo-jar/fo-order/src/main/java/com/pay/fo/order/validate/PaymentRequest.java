/**
 *  File: Batch2bankRequest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate;

import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * 
 */
public class PaymentRequest {

	/**
	 * 会员编号
	 */
	private Long memberCode;

	/**
	 * 商户订单号
	 */
	private String foreignOrderId;

	/**
	 * 收款方名称
	 */
	private String payeeName;

	/**
	 * 收款方银行账号
	 */
	private String payeeBankAcctCode;

	/**
	 * 收款方账户类型（交易类型）
	 */
	private String tradeType;

	/**
	 * 收款行银行名称
	 */
	private String payeeBankName;

	/**
	 * 收款方开户行名称
	 */
	private String payeeOpeningBankName;

	/**
	 * 收款行所在省份名称
	 */
	private String payeeBankProvinceName;

	/**
	 * 收款行所在城市名称
	 */
	private String payeeBankCityName;

	/**
	 * 请求付款金额
	 */
	private String requestAmount;

	/**
	 * 风控规则
	 */
	private RCLimitResultDTO riskRule;

	/**
	 * excel行数
	 */
	private Integer row;

	/**
	 * 验证返回信息.
	 */
	private PaymentResponse paymentResponse;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 收款行银行代码
	 */
	private String payeeBankCode;
	
	//----------------------added  PengJiangbo
	/**
	 * 收款方账户类型
	 */
	private Integer payerAcctType;
	/**
	 * 收款方账户类型
	 */
	private Integer payeeAcctType;
	/**
	 * 付款方币种代码
	 */
	private String payerCurrencyCode;
	/**
	 * 收款方币种代码
	 */
	private String payeeCurrencyCode;
	//----------------------added  PengJiangbo end

	public RCLimitResultDTO getRiskRule() {
		return riskRule;
	}

	public void setRiskRule(final RCLimitResultDTO riskRule) {
		this.riskRule = riskRule;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}

	public PaymentRequest() {
		this.paymentResponse = new PaymentResponse();
	}

	public PaymentResponse getPaymentResponse() {
		return paymentResponse;
	}

	public String getForeignOrderId() {
		return foreignOrderId;
	}

	public void setForeignOrderId(final String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(final String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}

	public void setPayeeBankAcctCode(final String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(final String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(final String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}

	public void setPayeeOpeningBankName(final String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}

	public void setPayeeBankProvinceName(final String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}

	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}

	public void setPayeeBankCityName(final String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}

	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(final String requestAmount) {
		this.requestAmount = requestAmount;
	}

	public void setPaymentResponse(final PaymentResponse paymentResponse) {
		this.paymentResponse = paymentResponse;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(final Integer row) {
		this.row = row;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(final String remark) {
		this.remark = remark;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(final String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(final Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(final Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(final String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(final String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

}
