/**
 * 
 */
package com.pay.acc.deal.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class BalanceEntryDto implements Serializable {
	private Long id;
	/* 帐户代码 */
	private String acctcode;
	/* 借贷方向 1借，2贷 */
	private Integer crdr;
	/* 金额 */
	private Long value;
	/* ma更新余额方向 1 为正 2为负 */
	private Integer maBlanceBy;

	private Date createdate;

	private Integer entrycode;

	/**
	 * 流水号
	 */
	private String dealId;

	private Integer paymentServiceId;

	private Integer status;

	private String text;

	private Integer entryType;

	private Long vouchercode;

	private String currencyCode;

	private Long exchangeRate;

	private Date transactiondate;

	private Date createDate;

	private Date updateDate;

	private Date payDate;

	private Long balance;

	private Date postDate;

	/**
	 * 交易号
	 */
	private Integer dealCode;

	public Integer getEntryType() {
		return entryType;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcctcode() {
		return acctcode;
	}

	public void setAcctcode(String acctcode) {
		this.acctcode = acctcode;
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

	public Integer getMaBlanceBy() {
		return maBlanceBy;
	}

	public void setMaBlanceBy(Integer maBlanceBy) {
		this.maBlanceBy = maBlanceBy;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getEntrycode() {
		return entrycode;
	}

	public void setEntrycode(Integer entrycode) {
		this.entrycode = entrycode;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Integer getPaymentServiceId() {
		return paymentServiceId;
	}

	public void setPaymentServiceId(Integer paymentServiceId) {
		this.paymentServiceId = paymentServiceId;
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

	public Long getVouchercode() {
		return vouchercode;
	}

	public void setVouchercode(Long vouchercode) {
		this.vouchercode = vouchercode;
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

	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the dealCode
	 */
	public Integer getDealCode() {
		return dealCode;
	}

	/**
	 * @param dealCode
	 *            the dealCode to set
	 */
	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BalanceEntryDto [acctcode=" + acctcode + ", balance=" + balance
				+ ", crdr=" + crdr + ", createDate=" + createDate
				+ ", createdate=" + createdate + ", currencyCode="
				+ currencyCode + ", dealCode=" + dealCode + ", dealId="
				+ dealId + ", entryType=" + entryType + ", entrycode="
				+ entrycode + ", exchangeRate=" + exchangeRate + ", id=" + id
				+ ", maBlanceBy=" + maBlanceBy + ", payDate=" + payDate
				+ ", paymentServiceId=" + paymentServiceId + ", postDate="
				+ postDate + ", status=" + status + ", text=" + text
				+ ", transactiondate=" + transactiondate + ", updateDate="
				+ updateDate + ", value=" + value + ", vouchercode="
				+ vouchercode + "]";
	}

}
