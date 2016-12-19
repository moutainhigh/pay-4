/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.dto;

import org.apache.commons.lang.StringUtils;

/**
 * 企业注册信息
 * @author zhi.wang
 * @version $Id: EnterpriseRegisterInfo.java, v 0.1 2011-2-21 上午11:00:58 zhi.wang Exp $
 */
public class EnterpriseRegisterInfo {
	/** Email登录账户 */
	private String email;
	/** 中文名 */
	private String zhName;
	/** 企业法人姓名*/
    private String legalName;
    /** 企业联系电话*/
    private String tel;
    /** 企业营业执照号码 */
    private String bizLicenceCode;
    /** 企业机构证件号码 */
    private String govCode;
    /** 企业税务等级证件号码 */
    private String taxCode;
    /** 公司联系人*/
    private String compayLinkerName;
    /** 公司联系电话*/
    private String compayLinkerTel;
    //联合登录的id号
    private String ssoUserId;
    //合同起始时间
    private String contractStartDate; 
    //合同结束时间
    private String contractEndDate; 
    
    private String contractFactStartDate; 
    private String contractFactEndDate; 
    
    private String corpAddress; 
    
    
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		// 转为小写
		this.email = StringUtils.lowerCase(email);;
	}
	public String getZhName() {
		return zhName;
	}
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBizLicenceCode() {
		return bizLicenceCode;
	}
	public void setBizLicenceCode(String bizLicenceCode) {
		this.bizLicenceCode = bizLicenceCode;
	}
	public String getGovCode() {
		return govCode;
	}
	public void setGovCode(String govCode) {
		this.govCode = govCode;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getCompayLinkerName() {
		return compayLinkerName;
	}
	public void setCompayLinkerName(String compayLinkerName) {
		this.compayLinkerName = compayLinkerName;
	}
	public String getCompayLinkerTel() {
		return compayLinkerTel;
	}
	public void setCompayLinkerTel(String compayLinkerTel) {
		this.compayLinkerTel = compayLinkerTel;
	}
	/**
	 * @return the ssoUserId
	 */
	public String getSsoUserId() {
		return ssoUserId;
	}
	/**
	 * @param ssoUserId the ssoUserId to set
	 */
	public void setSsoUserId(String ssoUserId) {
		this.ssoUserId = ssoUserId;
	}
	/**
	 * @return the contractStartDate
	 */
	public String getContractStartDate() {
		return contractStartDate;
	}
	/**
	 * @param contractStartDate the contractStartDate to set
	 */
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	/**
	 * @return the contractEndDate
	 */
	public String getContractEndDate() {
		return contractEndDate;
	}
	/**
	 * @param contractEndDate the contractEndDate to set
	 */
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	/**
	 * @return the contractFactStartDate
	 */
	public String getContractFactStartDate() {
		return contractFactStartDate;
	}
	/**
	 * @param contractFactStartDate the contractFactStartDate to set
	 */
	public void setContractFactStartDate(String contractFactStartDate) {
		this.contractFactStartDate = contractFactStartDate;
	}
	/**
	 * @return the contractFactEndDate
	 */
	public String getContractFactEndDate() {
		return contractFactEndDate;
	}
	/**
	 * @param contractFactEndDate the contractFactEndDate to set
	 */
	public void setContractFactEndDate(String contractFactEndDate) {
		this.contractFactEndDate = contractFactEndDate;
	}
	/**
	 * @return the corpAddress
	 */
	public String getCorpAddress() {
		return corpAddress;
	}
	/**
	 * @param corpAddress the corpAddress to set
	 */
	public void setCorpAddress(String corpAddress) {
		this.corpAddress = corpAddress;
	}
    
	
	
	
}
