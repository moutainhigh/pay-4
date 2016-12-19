/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.model;

import java.util.Date;

/**
 * 会员基本信息<br>
 * 对应ACC库
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午10:52:21
 * 
 */
public class TMember {
	
	/**会员号 */
	private long memberCode; 
	/** 类型 */
	private int type; 
	/** 服务级别代码 */
	private int serviceLevelCode; 
	/** 问候语 */
	private String greeting; 
	/** 状态 */
	private int status; 
	/** 安全问题ID */
	private int securityQuestion;
	/** 安全答案 */
	private String securityAnswer; 
	/** 与sso关联的用户id */
	private String ssoUserId; 
	/** 登录类型 */
	private int loginType; 
	/** 登录名 */
	private String loginName;
	/** 登录密码 */
	private String loginPwd; 
	/** 创建时间 */
	private Date createDate; 
	/** 修改时间 */
	private Date updateDate;
	
	public long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getServiceLevelCode() {
		return serviceLevelCode;
	}
	public void setServiceLevelCode(int serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(int securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getSsoUserId() {
		return ssoUserId;
	}
	public void setSsoUserId(String ssoUserId) {
		this.ssoUserId = ssoUserId;
	}
	public int getLoginType() {
		return loginType;
	}
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
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
