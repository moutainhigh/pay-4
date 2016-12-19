package com.pay.fi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BUYSETTLE_ORDER database table.
 * 
 */

public class BuysettleOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private long exchangeNo;
	
	private Date completedDate;

	private Date createDate;

	private String currencyCode;

	private BigDecimal exchAmount;

	private String exchCurrencyCode;

	private String exchangeRate;

	private String feeRate;

	private String fixedFee;

	private BigDecimal orderAmount;

	private BigDecimal partnerId;

	private String partnerName;

	private String percentFee;

	private String remark;

	private String source;

	private String status;

	private String tallestFee;

	private String type;

	private Date updateDate;
	
	private String middleParities;
	
	private BigDecimal tradeFee; 
	
	public BigDecimal getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(BigDecimal tradeFee) {
		this.tradeFee = tradeFee;
	}

	public BuysettleOrder() {
	}

	public long getExchangeNo() {
		return this.exchangeNo;
	}

	public void setExchangeNo(long exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getExchAmount() {
		return this.exchAmount;
	}

	public void setExchAmount(BigDecimal exchAmount) {
		this.exchAmount = exchAmount;
	}

	public String getExchCurrencyCode() {
		return this.exchCurrencyCode;
	}

	public void setExchCurrencyCode(String exchCurrencyCode) {
		this.exchCurrencyCode = exchCurrencyCode;
	}

	public String getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFeeRate() {
		return this.feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getFixedFee() {
		return this.fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(BigDecimal partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPercentFee() {
		return this.percentFee;
	}

	public void setPercentFee(String percentFee) {
		this.percentFee = percentFee;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTallestFee() {
		return this.tallestFee;
	}

	public void setTallestFee(String tallestFee) {
		this.tallestFee = tallestFee;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getMiddleParities() {
		return middleParities;
	}

	public void setMiddleParities(String middleParities) {
		this.middleParities = middleParities;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BuysettleOrder [exchangeNo=" + exchangeNo + ", completedDate=" + completedDate + ", createDate="
				+ createDate + ", currencyCode=" + currencyCode + ", exchAmount=" + exchAmount + ", exchCurrencyCode="
				+ exchCurrencyCode + ", exchangeRate=" + exchangeRate + ", feeRate=" + feeRate + ", fixedFee="
				+ fixedFee + ", orderAmount=" + orderAmount + ", partnerId=" + partnerId + ", partnerName="
				+ partnerName + ", percentFee=" + percentFee + ", remark=" + remark + ", source=" + source + ", status="
				+ status + ", tallestFee=" + tallestFee + ", type=" + type + ", updateDate=" + updateDate
				+ ", middleParities=" + middleParities + "]";
	}

	
}