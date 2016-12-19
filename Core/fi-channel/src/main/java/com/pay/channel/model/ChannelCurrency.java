package com.pay.channel.model;

import java.util.Date;

public class ChannelCurrency {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 主键
	 */
	private Long partnerId;
	/***
	 * 渠道编号
	 */
	private String orgCode;
	/**
	 * 产品描述
	 */
	private String prdtDesc;
	/**
	 * 产品编号
	 */
	private String prdtCode;
	/***
	 * 交易币种
	 */
	private String currencyCode;
	/**
	 * 卡本币
	 */
	private String cardCurrencyCode;
	/**
	 * 送渠道币种
	 */
	private String channelCurrencyCode;
	
	private String operator;
	
	private Date createDate;
	
	private Date updateDate;
	/***
	 * 支付类型
	 */
	private String payType;
	
	private String flag;
	
	private String status;

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrdtDesc() {
		return prdtDesc;
	}

	public void setPrdtDesc(String prdtDesc) {
		this.prdtDesc = prdtDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPrdtCode() {
		return prdtCode;
	}

	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCardCurrencyCode() {
		return cardCurrencyCode;
	}

	public void setCardCurrencyCode(String cardCurrencyCode) {
		this.cardCurrencyCode = cardCurrencyCode;
	}

	public String getChannelCurrencyCode() {
		return channelCurrencyCode;
	}

	public void setChannelCurrencyCode(String channelCurrencyCode) {
		this.channelCurrencyCode = channelCurrencyCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
