package com.pay.pe.model;

import java.util.Date;

import com.pay.inf.model.Model;

public class AccountEntry implements Model {

	private String acctCode;
	private Integer crdr;
	private Date creationDate;
	private Integer entryCode;
	private String dealId;
	private Integer paymentServiceCode;
	private Integer status;
	private String text;
	private Long value;
	private Long voucherCode;
	private Date postDate;
	private String currencyCode;
	private Long exchangeRate;
	private Date transactionDate;
	private Integer entryType;

	private Integer maBlanceBy;

	public Integer getEntryType() {
		return entryType;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public AccountEntry() {
		super();
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

	public Integer getCrdr() {
		return this.crdr;
	}

	public Integer getEntryCode() {
		return this.entryCode;
	}

	public Date getPostDate() {
		return this.postDate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getText() {
		return this.text;
	}

	public Long getValue() {
		return this.value;
	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public void setEntryCode(Integer entrycode) {
		this.entryCode = entrycode;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}

	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
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

	public Integer getMaBlanceBy() {
		return maBlanceBy;
	}

	public void setMaBlanceBy(Integer maBlanceBy) {
		this.maBlanceBy = maBlanceBy;
	}

}
