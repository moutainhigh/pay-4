package com.pay.fi.dto;

import java.util.Date;


public class CapitalPoolManage  {

	private long id;

	private Long buyAmount;

	private String currencyCode;

	private String operator;

	private Long precautiousLineAmount;

	private String status;

	private Date updateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Long buyAmount) {
		this.buyAmount = buyAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getPrecautiousLineAmount() {
		return precautiousLineAmount;
	}

	public void setPrecautiousLineAmount(Long precautiousLineAmount) {
		this.precautiousLineAmount = precautiousLineAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}