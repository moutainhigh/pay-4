/**
 * 
 */
package com.pay.acc.deal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class BalanceDeal implements Serializable {

	private Long id;
	/* 是否已经计费 */
	private Integer hasCaculatedPrice;
	/* 收款人费用 */
	private Long payeeFee;
	/* 付款人费用 */
	private Long payerFee;

	/**
	 * 流水号，long数值类型
	 */
	private String orderId;
	private Long orderAmount;
	private String merchantOrderId;

	private String submitAcctCode;

	private Long amount = 0L;

	private String paymentServicePkgCode;

	private Integer orderCode;
	private Integer dealCode;
	private Integer payMethod;

	// private int terminalType = TERMINALTYP.WEB.getValue();
	private int terminalType;

	private Date requestDate = new Date();
	/**
	 * 支付用户.
	 */
	private String payer;
	/**
	 * 付款方Member Account Code.
	 */
	private String payerMemberAcctCode;
	private Integer payerAcctType;

	/**
	 * 付款方FullMember Account Code. 包含科目号
	 */
	private String payerFullMemberAcctCode;
	/**
	 * 付款方机构类型代码.
	 */
	private Integer payerOrgType;
	/**
	 * 支付用户的机构代码.
	 */
	private String payerOrgCode;

	private Integer payerServiceLevel = -1;
	private String payerCurrencyCode;

	/**
	 * 收款用户.
	 */
	private String payee;

	/**
	 * 收款用户的机构代码.
	 */
	private String payeeOrgCode;

	/**
	 * 收款方机构类型代码.
	 */
	private Integer payeeOrgType;

	/**
	 * 收款方Member Account Code.
	 */
	private String payeeMemberAcctCode;

	private Integer payeeAcctType;

	/**
	 * 收款方FullMember Account Code. 包含科目号
	 */
	private String payeeFullMemberAcctCode;

	private Integer payeeServiceLevel = -1;

	private String payeeCurrencyCode;

	private Long exchangeRate;

	private Integer chargeUpStatus;

	private Integer dealType;

	private Long priceStrategyCode;

	private Long voucherCode;
	
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(Long voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHasCaculatedPrice() {
		return hasCaculatedPrice;
	}

	public void setHasCaculatedPrice(Integer hasCaculatedPrice) {
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

	public String getSubmitAcctCode() {
		return submitAcctCode;
	}

	public void setSubmitAcctCode(String submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}

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

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
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

	public String getPayerMemberAcctCode() {
		return payerMemberAcctCode;
	}

	public void setPayerMemberAcctCode(String payerMemberAcctCode) {
		this.payerMemberAcctCode = payerMemberAcctCode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerFullMemberAcctCode() {
		return payerFullMemberAcctCode;
	}

	public void setPayerFullMemberAcctCode(String payerFullMemberAcctCode) {
		this.payerFullMemberAcctCode = payerFullMemberAcctCode;
	}

	public Integer getPayerOrgType() {
		return payerOrgType;
	}

	public void setPayerOrgType(Integer payerOrgType) {
		this.payerOrgType = payerOrgType;
	}

	public String getPayerOrgCode() {
		return payerOrgCode;
	}

	public void setPayerOrgCode(String payerOrgCode) {
		this.payerOrgCode = payerOrgCode;
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

	public Integer getPayeeOrgType() {
		return payeeOrgType;
	}

	public void setPayeeOrgType(Integer payeeOrgType) {
		this.payeeOrgType = payeeOrgType;
	}

	public String getPayeeMemberAcctCode() {
		return payeeMemberAcctCode;
	}

	public void setPayeeMemberAcctCode(String payeeMemberAcctCode) {
		this.payeeMemberAcctCode = payeeMemberAcctCode;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayeeFullMemberAcctCode() {
		return payeeFullMemberAcctCode;
	}

	public void setPayeeFullMemberAcctCode(String payeeFullMemberAcctCode) {
		this.payeeFullMemberAcctCode = payeeFullMemberAcctCode;
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

	public Integer getChargeUpStatus() {
		return chargeUpStatus;
	}

	public void setChargeUpStatus(Integer chargeUpStatus) {
		this.chargeUpStatus = chargeUpStatus;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public Long getPriceStrategyCode() {
		return priceStrategyCode;
	}

	public void setPriceStrategyCode(Long priceStrategyCode) {
		this.priceStrategyCode = priceStrategyCode;
	}

	@Override
	public String toString() {
		return "BalanceDeal [amount=" + amount + ", chargeUpStatus="
				+ chargeUpStatus + ", dealCode=" + dealCode + ", dealType="
				+ dealType + ", exchangeRate=" + exchangeRate
				+ ", hasCaculatedPrice=" + hasCaculatedPrice + ", id=" + id
				+ ", orderAmount=" + orderAmount + ", orderCode=" + orderCode
				+ ", orderId=" + orderId + ", payMethod=" + payMethod
				+ ", payee=" + payee + ", payeeAcctType=" + payeeAcctType
				+ ", payeeCurrencyCode=" + payeeCurrencyCode + ", payeeFee="
				+ payeeFee + ", payeeFullMemberAcctCode="
				+ payeeFullMemberAcctCode + ", payeeMemberAcctCode="
				+ payeeMemberAcctCode + ", payeeOrgCode=" + payeeOrgCode
				+ ", payeeOrgType=" + payeeOrgType + ", payeeServiceLevel="
				+ payeeServiceLevel + ", payer=" + payer + ", payerAcctType="
				+ payerAcctType + ", payerCurrencyCode=" + payerCurrencyCode
				+ ", payerFee=" + payerFee + ", payerFullMemberAcctCode="
				+ payerFullMemberAcctCode + ", payerMemberAcctCode="
				+ payerMemberAcctCode + ", payerOrgCode=" + payerOrgCode
				+ ", payerOrgType=" + payerOrgType + ", payerServiceLevel="
				+ payerServiceLevel + ", paymentServicePkgCode="
				+ paymentServicePkgCode + ", requestDate=" + requestDate
				+ ", submitAcctCode=" + submitAcctCode + ", terminalType="
				+ terminalType + "]";
	}

}
