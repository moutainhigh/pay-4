package com.pay.channel.model;

import java.util.Date;

public class ChannelSndrelationDate {

	private String orgCode;
	
	private String orgMerchantCode;
	
	private String cardOrg;
	
	private Date createRelateDate;
	
	private Date cancelRelateDate;
	
	private Date createDate;
	
	private Date updDate;
	
	private String operator;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}

	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
