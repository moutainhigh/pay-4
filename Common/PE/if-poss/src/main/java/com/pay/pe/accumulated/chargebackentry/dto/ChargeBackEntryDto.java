package com.pay.pe.accumulated.chargebackentry.dto;

import java.util.Date;

public class ChargeBackEntryDto {

	private String 	acctCode;
	private Integer 	crdr;
	private Long 	value;
	private Integer mablanceBy;
	private Integer paymentServiceCode;
	private String currencyCode;
	private Long  exchangeRate;
	private Date creationDate;
	private Integer entryCode;
	private String dealid;
	private Integer status;
	private String text;
	private Long voucherCode;
	private Date transactionDate;
	private Integer dealType;
	
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public Integer getCrdr() {
		return crdr;
	}
	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Integer getMablanceBy() {
		return mablanceBy;
	}
	public void setMablanceBy(Integer mablanceBy) {
		this.mablanceBy = mablanceBy;
	}
	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}
	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Long getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(Integer entryCode) {
		this.entryCode = entryCode;
	}
	public String getDealid() {
		return dealid;
	}
	public void setDealid(String dealid) {
		this.dealid = dealid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(Long voucherCode) {
		this.voucherCode = voucherCode;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

}
