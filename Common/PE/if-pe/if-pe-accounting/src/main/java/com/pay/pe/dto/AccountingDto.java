package com.pay.pe.dto;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class AccountingDto implements Serializable {
	private static final long serialVersionUID = -416410400273030216L;

	/**
	 * 记账订单号
	 */
	private String orderId;
	
	/**
	 * 商户订单号
	 */
	private String merchantOrderId;
	
	/**
	 * 记账金额
	 */
	private Long amount;
	/**
	 * 订单金额
	 */
	private Long orderAmount;
	/**
	 * 付款方手续费
	 */
	private Long payerFee;

	/**
	 * 收款方手续费
	 */
	private Long payeeFee;
	/**
	 * 付款方memberCode
	 */
	private Long payer;

	private String payerFullMemberAcctCode;

	private Integer payerMemberType;

	private Integer payerAcctType;

	private Long payee;

	private String payeeFullMemberAcctCode;

	private Integer payeeMemberType;

	private Integer payeeAcctType;

	private boolean hasCaculatedPrice;

	/**
	 * 汇y
	 */
	private Long exchangeRate;

	/**
	 * 银行code，用来映射银行记账科目
	 */
	private String bankCode;
	
	private String payerCurrencyCode;

	private String payeeCurrencyCode;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderId(Long orderId) {
		if (null != orderId) {
			this.orderId = String.valueOf(orderId);
		}
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setAmount(String amount) {
		this.amount = Long.valueOf(amount);
	}

	public void setAmount(BigDecimal amount) {
		if (null != amount) {
			this.amount = amount.longValue();
		}
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPayer() {
		return payer;
	}

	public void setPayer(Long payer) {
		this.payer = payer;
	}

	public void setPayer(String payer) {
		if (null != payer && payer.length() > 0) {
			this.payer = Long.valueOf(payer);
		}
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		if (null != orderAmount) {
			this.orderAmount = orderAmount.longValue();
		}
	}

	public void setOrderAmount(String orderAmount) {
		if (null != orderAmount && orderAmount.length() != 0) {
			this.orderAmount = Long.valueOf(orderAmount);
		}
	}

	public Long getPayee() {
		return payee;
	}

	public void setPayee(Long payee) {
		this.payee = payee;
	}

	public void setPayee(String payee) {
		if (null != payee && payee.length() > 0) {
			this.payee = Long.valueOf(payee);
		}
	}

	public boolean isHasCaculatedPrice() {
		return hasCaculatedPrice;
	}

	public void setHasCaculatedPrice(boolean hasCaculatedPrice) {
		this.hasCaculatedPrice = hasCaculatedPrice;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	public String getPayerFullMemberAcctCode() {
		return payerFullMemberAcctCode;
	}

	public void setPayerFullMemberAcctCode(String payerFullMemberAcctCode) {
		this.payerFullMemberAcctCode = payerFullMemberAcctCode;
	}

	public String getPayeeFullMemberAcctCode() {
		return payeeFullMemberAcctCode;
	}

	public void setPayeeFullMemberAcctCode(String payeeFullMemberAcctCode) {
		this.payeeFullMemberAcctCode = payeeFullMemberAcctCode;
	}

	public Integer getPayerMemberType() {
		return payerMemberType;
	}

	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
	}

	public Integer getPayeeMemberType() {
		return payeeMemberType;
	}

	public void setPayeeMemberType(Integer payeeMemberType) {
		this.payeeMemberType = payeeMemberType;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		BeanWrapper beanWrapper = new BeanWrapperImpl(this);
		PropertyDescriptor[] propertyDescriptors = beanWrapper
				.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String propertyName = property.getDisplayName();
			Object value = beanWrapper.getPropertyValue(propertyName);
			if ("class".equals(propertyName)) {
				continue;
			}
			sb.append("&");
			sb.append(propertyName);
			sb.append("=");
			sb.append(value);
		}
		return sb.toString();
	}

}
