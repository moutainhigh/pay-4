package com.pay.poss.controller.fi.dto;

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
public class BouncedFraudResultDTO extends Object {

	private String channelOrderNo;	
	private String orgCode;	
	private String orgName;	
	private BigDecimal amount;
	private String merchantNo; 
	private String createDate; 
	private String currencyCode; 
	private String cardOrg; 
	private String settlementCurrencyCode; 
	private BigDecimal settlementRate; 
	private String partnerId; 
	private String partnerName; 
	private BigDecimal settlementAmount;
	//拒付笔数
	private BigDecimal bouncedCount;
	//总订单数
	private BigDecimal totalCount;
	private BigDecimal thistotalCount;
	//拒付率
	private BigDecimal bouncedRate;
	private String sbouncedRate;
	//预估拒付罚金
	private BigDecimal bouncedMulct;
	//欺诈金额
	private BigDecimal fraudAmount;
	//总交易额
	private BigDecimal totalAmount;
	private BigDecimal thistotalAmount;
	//欺诈金额比例
	private BigDecimal fraudRate;
	private String sfraudRate;
	
	
	
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
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public BigDecimal getBouncedCount() {
		return bouncedCount;
	}
	public void setBouncedCount(BigDecimal bouncedCount) {
		this.bouncedCount = bouncedCount;
	}
	public BigDecimal getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getBouncedRate() {
		return bouncedRate;
	}
	public void setBouncedRate(BigDecimal bouncedRate) {
		this.bouncedRate = bouncedRate;
	}
	public BigDecimal getBouncedMulct() {
		return bouncedMulct;
	}
	public void setBouncedMulct(BigDecimal bouncedMulct) {
		this.bouncedMulct = bouncedMulct;
	}
	public BigDecimal getFraudAmount() {
		return fraudAmount;
	}
	public void setFraudAmount(BigDecimal fraudAmount) {
		this.fraudAmount = fraudAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getFraudRate() {
		return fraudRate;
	}
	public void setFraudRate(BigDecimal fraudRate) {
		this.fraudRate = fraudRate;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSbouncedRate() {
		return sbouncedRate;
	}
	public void setSbouncedRate(String sbouncedRate) {
		this.sbouncedRate = sbouncedRate;
	}
	public String getSfraudRate() {
		return sfraudRate;
	}
	public void setSfraudRate(String sfraudRate) {
		this.sfraudRate = sfraudRate;
	}
	public BigDecimal getThistotalCount() {
		return thistotalCount;
	}
	public void setThistotalCount(BigDecimal thistotalCount) {
		this.thistotalCount = thistotalCount;
	}
	public BigDecimal getThistotalAmount() {
		return thistotalAmount;
	}
	public void setThistotalAmount(BigDecimal thistotalAmount) {
		this.thistotalAmount = thistotalAmount;
	} 

	


}