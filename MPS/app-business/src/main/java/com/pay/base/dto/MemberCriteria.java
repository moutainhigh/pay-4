/**
 *  File: MemberQueryCriteria.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-15   terry_ma     Create
 *
 */
package com.pay.base.dto;

import java.util.Date;

import com.pay.util.StringUtil;

/**
 * 会员相关信息
 */
public class MemberCriteria implements java.io.Serializable {

	/**
	 * 会员userId.
	 */
	private String userId;

	/**
	 * 会员标识
	 */
	private String memberCode;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String mobileNo;
	/**
	 * 真实姓名
	 */
	private String verifyName;
	/**
	 * 支付密码
	 */
	private String payPwd;
	/**
	 * 问题id
	 */
	private String questionId;
	/**
	 * 安全问题
	 */
	private String question;

	/**
	 * 安全问题答案
	 */
	private String answer;
	/**
	 * 欢迎词
	 */
	private String salutatory;
	/**
	 * 证件类型
	 */
	private Integer cardType;
	/**
	 * 证件号码
	 */
	private String cardNo;
	/**
	 * 固定电话
	 */
	private String aptoticPhone;
	/**
	 * 传真
	 */
	private String faxes;
	/**
	 * 腾讯QQ
	 */
	private String qq;
	/**
	 * 
	 */
	private String msn;
	/**
	 * 地区
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String postCode;

	/**
	 *激活状态(status:激活，未激活，冻结，正常)
	 */
	private String status;

	private String welcomeMsg;

	private String userName;

	private Date updateDate;

	// 注册时间
	private Date createDate;

	private Integer memberType;

	private String loginName;

	private String regType;
	// 问候语
	private String greeting;

	private Long operatorId;
	/** 上次登录时间 */
	private String lastLoginTime;
	/** 服务级别 */
	private Integer serviceLevel;
	
	public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
	 * 联系方式（手机或email）
	 */
	private String contact;

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSalutatory() {

		return salutatory;
	}

	public void setSalutatory(String salutatory) {
		this.salutatory = salutatory;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAptoticPhone() {
		return aptoticPhone;
	}

	public void setAptoticPhone(String aptoticPhone) {
		this.aptoticPhone = aptoticPhone;
	}

	public String getFaxes() {
		return faxes;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public void setFaxes(String faxes) {
		this.faxes = faxes;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getWelcomeMsg() {

		return welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	public Integer getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(Integer serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

}
