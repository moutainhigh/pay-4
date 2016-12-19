/*
   File:          用户注册BO
   Description:   用户注册与MA接口需求
   Copyright (c) 2004-2013 pay.com . All rights reserved. 
   Date           Author                              Changes
   2010-8-11      wolf_huang@staff.pay.com	   	      wolf_huang@staff.pay.com
   2010-8-16      Jeffrey_teng@staff.pay.com	   	  Jeffrey_teng@staff.pay.com
*/
package com.pay.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class MemberInfoDto {
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
	private String loginType;
	
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
	private String cerType;
	
	/**
	 * 证件号码
	 */
	private String cerCode;
	
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
	private String ssoUserId;
	
	/**
	 * 地址
	 */
	private String addr;

	/**
	 * 注册时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNewUserName() {
		return newUserName;
	}
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getNewPayPassword() {
		return newPayPassword;
	}
	public void setNewPayPassword(String newPayPassword) {
		this.newPayPassword = newPayPassword;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	public String getCerCode() {
		return cerCode;
	}
	public void setCerCode(String cerCode) {
		this.cerCode = cerCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getSsoUserId() {
		return ssoUserId;
	}
	public void setSsoUserId(String ssoUserId) {
		this.ssoUserId = ssoUserId;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
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
