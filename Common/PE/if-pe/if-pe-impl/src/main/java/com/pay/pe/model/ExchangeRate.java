package com.pay.pe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.model.Model;

public class ExchangeRate implements Model{
	private Long sequenceId;
	private String currencyCodeFrom;
	private String currencyCodeTo;
	private Long exchangeRate;
	private Date startDate;
	private Date endDate;
	private Integer status;
	private Date createDate;
	
	
	private static List <String> pk = new ArrayList <String> ();
	static {
		pk.add("sequenceId");
	}

	
	public Object getPrimaryKey() {
		Object[] obj = new Object[]{sequenceId};
		return obj;
	}

	
	public List getPrimaryKeyFields() {
		return pk;
	}

	
	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[])key;
			setSequenceId((Long)obj[0]);
		}
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getCurrencyCodeFrom() {
		return currencyCodeFrom;
	}

	public void setCurrencyCodeFrom(String currencyCodeFrom) {
		this.currencyCodeFrom = currencyCodeFrom;
	}

	public String getCurrencyCodeTo() {
		return currencyCodeTo;
	}

	public void setCurrencyCodeTo(String currencyCodeTo) {
		this.currencyCodeTo = currencyCodeTo;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
