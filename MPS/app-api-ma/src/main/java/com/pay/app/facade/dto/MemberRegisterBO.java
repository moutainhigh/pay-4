/*
   File:          用户注册BO
   Description:   用户注册与MA接口需求
   Copyright (c) 2004-2013 pay.com . All rights reserved. 
   Date           Author                              Changes
   2010-8-11      wolf_huang@staff.pay.com	   	      wolf_huang@staff.pay.com
   2010-8-16      Jeffrey_teng@staff.pay.com	   	  Jeffrey_teng@staff.pay.com
*/
package com.pay.app.facade.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

/** 
 * <p>用户注册BO</p>
 * 
 * 用户注册与MA接口需求
 * 
 * @author  wolf_huang@staff.pay.com
 * @date    2010-8-11
*/ 
public class MemberRegisterBO {
	/**
	 * 登陆名（手机或email）
	 */
	@NotNull 
	private String loginName;
	
	/**
	 * email
	 */
	private String email;
	/**
	 * mobile
	 */
	private String mobile;
	
	/**
	 * 新用户名
	 */
	private String newUserName;
	
	/**
	 * 姓名
	 */
	private String realName;
	
	/**
	 * 登录密码
	 */
	@NotNull
	private String password;
	/**
	 * 注册方式
	 */
	@NotNull
	private String regType;
	
	/**
	 * 登录密码
	 */
	private String newPassword;
	
	
	/**
	 * 支付密码
	 */
	private String payPassword;
	
	/**
	 * 新支付密码
	 */
	private String newPayPassword;
	
	/**
	 * 安全保护问题id
	 */	
	private String securityQuestion;
	
	/**
	 * 安全保护问题答案
	 */	
	private String securityAnswer;
	
	/**
	 * 问候语
	 */
	private String greeting;
	/**
	 * 联系方式（手机或email）
	 */
	private String contact;
	
	/**
	 * 身份证类型id
	 */
	private String certificateType;
	
	/**
	 * 证件号码
	 */
	private String certificateNo;
	
	/**
	 * 固定电话
	 */
	private String tel;
	
	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * qq
	 */
	private String qq;
	
	/**
	 * MSN
	 */
	private String msn;
	
	/**
	 * 省份id
	 */
	private String province;
	
	/**
	 * 城市id
	 */
	private String city;
	
	/**
	 * 邮编
	 */
	private String zip;
		
	
	/**
	 * 会员编号
	 */
	private Long memberCode;
		
	/**
	 * Sso_userId
	 */
	private String userId;
	
	/**
	 * Sso_userId 来源
	 */
	private String userIdSource;
	
	/**
	 * 地址
	 */
	private String address;

	/**
	 * 注册时间
	 */
	private Date creationDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * @return the newUserName
	 */
	public String getNewUserName() {
		return newUserName;
	}

	/**
	 * @param newUserName the newUserName to set
	 */
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the payPassword
	 */
	public String getPayPassword() {
		return payPassword;
	}

	/**
	 * @param payPassword the payPassword to set
	 */
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	/**
	 * @return the newPayPassword
	 */
	public String getNewPayPassword() {
		return newPayPassword;
	}

	/**
	 * @param newPayPassword the newPayPassword to set
	 */
	public void setNewPayPassword(String newPayPassword) {
		this.newPayPassword = newPayPassword;
	}

	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @param securityAnswer the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return the greeting
	 */
	public String getGreeting() {
		return greeting;
	}

	/**
	 * @param greeting the greeting to set
	 */
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}


	/**
	 * @return the certificateType
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * @param certificateType the certificateType to set
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * @return the certificateNo
	 */
	public String getCertificateNo() {
		return certificateNo;
	}

	/**
	 * @param certificateNo the certificateNo to set
	 */
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}





	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}




	/**
	 * @return the userIdSource
	 */
	public String getUserIdSource() {
		return userIdSource;
	}

	/**
	 * @param userIdSource the userIdSource to set
	 */
	public void setUserIdSource(String userIdSource) {
		this.userIdSource = userIdSource;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
