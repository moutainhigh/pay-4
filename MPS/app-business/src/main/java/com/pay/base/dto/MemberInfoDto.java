/*
   File:          用户注册BO
   Description:   用户注册与MA接口需求
   Copyright (c) 2004-2013 pay.com . All rights reserved. 
   Date           Author                              Changes
   2010-8-11      wolf_huang@staff.pay.com	   	      wolf_huang@staff.pay.com
   2010-8-16      Jeffrey_teng@staff.pay.com	   	  Jeffrey_teng@staff.pay.com
 */
package com.pay.base.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pay.app.dto.TokenDto;
import com.pay.inf.dto.MutableDto;

public class MemberInfoDto extends TokenDto implements MutableDto {

	private static final long serialVersionUID = 1L;

	/**
	 * 登陆名（手机或email）
	 */
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
	private String password;
	/**
	 * 注册方式
	 */
	private Integer regType;

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
	private Integer securityQuestion;

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
	private Integer certificateType;

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
	/**
	 * 状态
	 */
	private Integer status;

	private String ssoUserName;

	private String sex;

	private String profession;

	private String country;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSsoUserName() {
		return ssoUserName;
	}

	public void setSsoUserName(String ssoUserName) {
		this.ssoUserName = ssoUserName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		if (StringUtils.isNotBlank(loginName))
			this.loginName = loginName.toLowerCase();
		else
			this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (StringUtils.isNotBlank(email))
			this.email = email.toLowerCase();
		else
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

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
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

	public Integer getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(Integer securityQuestion) {
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

	public Integer getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(Integer certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
