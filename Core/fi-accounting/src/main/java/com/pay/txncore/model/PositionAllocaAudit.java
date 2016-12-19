package com.pay.txncore.model;

import java.util.Date;


public class PositionAllocaAudit  {

	private long allotSequence;

	private String callinAccount;

	private Long callinAmount;

	private Long callinBuyAmount;

	private String callinCurrencyCode;

	private String calloutAccount;

	private Long calloutAmount;

	private Long calloutBuyAmount;

	private String calloutCurrencyCode;

	private Date createDate;

	private String beginCreateDate;
	
	private String endCreateDate;
	
	private String operator;

	private String status;

	private Date updateDate;


	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public long getAllotSequence() {
		return this.allotSequence;
	}

	public void setAllotSequence(long allotSequence) {
		this.allotSequence = allotSequence;
	}

	public String getCallinAccount() {
		return this.callinAccount;
	}

	public void setCallinAccount(String callinAccount) {
		this.callinAccount = callinAccount;
	}

	public Long getCallinAmount() {
		return this.callinAmount;
	}

	public void setCallinAmount(Long callinAmount) {
		this.callinAmount = callinAmount;
	}

	public Long getCallinBuyAmount() {
		return this.callinBuyAmount;
	}

	public void setCallinBuyAmount(Long callinBuyAmount) {
		this.callinBuyAmount = callinBuyAmount;
	}

	public String getCallinCurrencyCode() {
		return this.callinCurrencyCode;
	}

	public void setCallinCurrencyCode(String callinCurrencyCode) {
		this.callinCurrencyCode = callinCurrencyCode;
	}

	public String getCalloutAccount() {
		return this.calloutAccount;
	}

	public void setCalloutAccount(String calloutAccount) {
		this.calloutAccount = calloutAccount;
	}

	public Long getCalloutAmount() {
		return this.calloutAmount;
	}

	public void setCalloutAmount(Long calloutAmount) {
		this.calloutAmount = calloutAmount;
	}

	public Long getCalloutBuyAmount() {
		return this.calloutBuyAmount;
	}

	public void setCalloutBuyAmount(Long calloutBuyAmount) {
		this.calloutBuyAmount = calloutBuyAmount;
	}

	public String getCalloutCurrencyCode() {
		return this.calloutCurrencyCode;
	}

	public void setCalloutCurrencyCode(String calloutCurrencyCode) {
		this.calloutCurrencyCode = calloutCurrencyCode;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}