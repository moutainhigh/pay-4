package com.pay.channel.model;

import java.util.Date;

public class ChannelSndRelation {
	
	/***
	 * 会员号
	 */
	private String memberCode;
	/**
	 * 通道名称
	 */
	private String paymentItemName;
	/**
	 * 二级商户号
	 */
	private String orgMerchantCode;
	/**
	 * 交易类型
	 */
	private String transType;
	/**
	 * 模式
	 */
	private String pattern;
	/**
	 * 卡组织
	 */
	private String cardOrg;
	 /**
	  * 拒付比例
	  */
	private String cptPercent;
	/**
	 * 交易笔数
	 */
	private String tradeCnt;
	/**
	 * 最后使用开始时间
	 */
	private Date createRelateDate;
	/**
	 * 最后使用结束时间
	 */
	private Date cancelRelateDate;

	private String fitMerchantType;

	private Long id;

	public String getFitMerchantType() {
		return fitMerchantType;
	}

	public void setFitMerchantType(String fitMerchantType) {
		this.fitMerchantType = fitMerchantType;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPaymentItemName() {
		return paymentItemName;
	}

	public void setPaymentItemName(String paymentItemName) {
		this.paymentItemName = paymentItemName;
	}

	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}

	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getCptPercent() {
		return cptPercent;
	}

	public void setCptPercent(String cptPercent) {
		this.cptPercent = cptPercent;
	}

	public String getTradeCnt() {
		return tradeCnt;
	}

	public void setTradeCnt(String tradeCnt) {
		this.tradeCnt = tradeCnt;
	}

	public Date getCreateRelateDate() {
		return createRelateDate;
	}

	public void setCreateRelateDate(Date createRelateDate) {
		this.createRelateDate = createRelateDate;
	}

	public Date getCancelRelateDate() {
		return cancelRelateDate;
	}

	public void setCancelRelateDate(Date cancelRelateDate) {
		this.cancelRelateDate = cancelRelateDate;
	}
	
}
