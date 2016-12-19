package com.pay.txncore.model;

import java.io.Serializable;
import java.util.Date;

public class TradeAmountCount implements Serializable{

	private static final long serialVersionUID = -5235398409942728859L;

	private Long id;
	
	private String partnerId;
	
	private String countMonth;
	
	private Long totalAmount;
	
	private String totalCurrencyCode;
	
	private Date createDate;
	
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCountMonth() {
		return countMonth;
	}

	public void setCountMonth(String countMonth) {
		this.countMonth = countMonth;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalCurrencyCode() {
		return totalCurrencyCode;
	}

	public void setTotalCurrencyCode(String totalCurrencyCode) {
		this.totalCurrencyCode = totalCurrencyCode;
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

	@Override
	public String toString() {
		return "TradeAmountCount [id=" + id + ", partnerId=" + partnerId
				+ ", countMonth=" + countMonth + ", totalAmount=" + totalAmount
				+ ", totalCurrencyCode=" + totalCurrencyCode + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
	
}
