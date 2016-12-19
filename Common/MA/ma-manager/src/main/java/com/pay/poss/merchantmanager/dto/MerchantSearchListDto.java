package com.pay.poss.merchantmanager.dto;


/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		MerchantSearchDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-23		gungun_zhang			Create
 */
 
public class MerchantSearchListDto {
	/**
	 *  商户id
	 */
	private String memberCode;
	/**
	 *  商户号
	 */
	private String merchantCode;
	
	/**
	 *  商户名称
	 */
	private String merchantName;
	/**
	 *  商户类型(企业,个人)
	 */
	private String merchantType;
	/**
	 *  服务等级(普通商户,大型...)
	 */
	private String serviceLevel;
	/**
	 *  商户状态
	 */
	private String merchantStatus;
	/**
	 *  商户所属行业
	 */
	private String industry;
	/**
	 * 商户审核状态
	 */
	private String merchantCheckStatus;
	
	/**
	 *  审核通过时间
	 */
	private String checkCreateDate;
	/**
	 *  的商户接收激活url的email
	 */
	private String email;
	/**
	 *  补发email标志
	 */
	private String isSendEmail;
	
	
	
	public String getIsSendEmail() {
		return isSendEmail;
	}
	public void setIsSendEmail(String isSendEmail) {
		this.isSendEmail = isSendEmail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCheckCreateDate() {
		return checkCreateDate;
	}
	public void setCheckCreateDate(String checkCreateDate) {
		this.checkCreateDate = checkCreateDate;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getMerchantStatus() {
		return merchantStatus;
	}
	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getMerchantCheckStatus() {
		return merchantCheckStatus;
	}
	public void setMerchantCheckStatus(String merchantCheckStatus) {
		this.merchantCheckStatus = merchantCheckStatus;
	}



	

	
	    
}
