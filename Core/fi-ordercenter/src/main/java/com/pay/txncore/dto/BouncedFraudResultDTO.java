package com.pay.txncore.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拒付欺诈结果表
 * File: BouncedResultDTO.java Description: 
 * Copyright 2016-2030 IPAYLINKS Corporation. 
 * All rights reserved. 
 * Date Author Changes 2016年5月10日
 * mmzhang Create
 *
 */
public class BouncedFraudResultDTO {

	private String channelOrderNo;	
	private String orgCode;	
	private BigDecimal amount;
	private BigDecimal payAmount;
	private String merchantNo; 
	private String createDate; 
	private String currencyCode; 
	private String transferCurrencyCode; 
	private String cardOrg; 
	private String settlementCurrencyCode; 
	private BigDecimal settlementRate; 
	private String partnerId; 
	private String merchantCode; 
	private String partnerName; 
	private BigDecimal settlementAmount;
	
	public String getChannelOrderNo() {
		return channelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCardOrg() {
		return cardOrg;
	}
	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}
	public BigDecimal getSettlementRate() {
		return settlementRate;
	}
	public void setSettlementRate(BigDecimal settlementRate) {
		this.settlementRate = settlementRate;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getTransferCurrencyCode() {
		return transferCurrencyCode;
	}
	public void setTransferCurrencyCode(String transferCurrencyCode) {
		this.transferCurrencyCode = transferCurrencyCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	} 

	


}