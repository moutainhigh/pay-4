/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.model;

import java.util.Date;

/**
 * 企业合同信息
 * @author zhi.wang
 * @version $Id: EnterpriseContract.java, v 0.1 2011-2-21 下午02:47:37 zhi.wang Exp $
 */
public class EnterpriseContract {
	private Long contractId;
	private Long memberCode;
	private String contractCode;
	private String signName;
	private String signDepart;
	private Integer continueSign;
	private Integer contractStatus;
	private String marketName;
	private String marketLink;
	private Date startDate;
	private Date endDate;
	private Long openFee;
	private Long yearFee;
	private Long factOpenFee;
	private Long factYearFee;
	private Date factStartDate;
	private Date factEndDate;
	private Long assureFee;
	private Long factAssureFee;
	private String assureDesc;
	private Date createDate;
	private Date updateDate;
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getSignDepart() {
		return signDepart;
	}
	public void setSignDepart(String signDepart) {
		this.signDepart = signDepart;
	}
	public Integer getContinueSign() {
		return continueSign;
	}
	public void setContinueSign(Integer continueSign) {
		this.continueSign = continueSign;
	}
	public Integer getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getMarketLink() {
		return marketLink;
	}
	public void setMarketLink(String marketLink) {
		this.marketLink = marketLink;
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
	public Long getOpenFee() {
		return openFee;
	}
	public void setOpenFee(Long openFee) {
		this.openFee = openFee;
	}
	public Long getYearFee() {
		return yearFee;
	}
	public void setYearFee(Long yearFee) {
		this.yearFee = yearFee;
	}
	public Long getFactOpenFee() {
		return factOpenFee;
	}
	public void setFactOpenFee(Long factOpenFee) {
		this.factOpenFee = factOpenFee;
	}
	public Long getFactYearFee() {
		return factYearFee;
	}
	public void setFactYearFee(Long factYearFee) {
		this.factYearFee = factYearFee;
	}
	public Date getFactStartDate() {
		return factStartDate;
	}
	public void setFactStartDate(Date factStartDate) {
		this.factStartDate = factStartDate;
	}
	public Date getFactEndDate() {
		return factEndDate;
	}
	public void setFactEndDate(Date factEndDate) {
		this.factEndDate = factEndDate;
	}
	public Long getAssureFee() {
		return assureFee;
	}
	public void setAssureFee(Long assureFee) {
		this.assureFee = assureFee;
	}
	public Long getFactAssureFee() {
		return factAssureFee;
	}
	public void setFactAssureFee(Long factAssureFee) {
		this.factAssureFee = factAssureFee;
	}
	public String getAssureDesc() {
		return assureDesc;
	}
	public void setAssureDesc(String assureDesc) {
		this.assureDesc = assureDesc;
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
	
}
