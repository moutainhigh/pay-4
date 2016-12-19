package com.pay.acct.buySettleParities.model;

import java.util.Date;

public class BuysettlePoundageConfig  {

	private long id;

	private Long capValue;

	private Date createDate;

	private String currencyCode;

	private Long fixedFee;

	private Long minimumValue;

	private String operator;

	private Long partnerId;

	private String percentageFee;

	private Integer status;

	private Date updateDate;
	
	private String createDateS;
	
	private String updateDateS;
	
	public String getCreateDateS() {
		return createDateS;
	}

	public void setCreateDateS(String createDateS) {
		this.createDateS = createDateS;
	}

	public String getUpdateDateS() {
		return updateDateS;
	}

	public void setUpdateDateS(String updateDateS) {
		this.updateDateS = updateDateS;
	}

	public BuysettlePoundageConfig() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCapValue() {
		return capValue;
	}

	public void setCapValue(Long capValue) {
		this.capValue = capValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(Long fixedFee) {
		this.fixedFee = fixedFee;
	}

	public Long getMinimumValue() {
		return minimumValue;
	}

	public void setMinimumValue(Long minimumValue) {
		this.minimumValue = minimumValue;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getPercentageFee() {
		return percentageFee;
	}

	public void setPercentageFee(String percentageFee) {
		this.percentageFee = percentageFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}