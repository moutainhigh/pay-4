package com.pay.poss.externalmanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file TMember.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-18 gungun_zhang Create
 */
public class Member extends BaseObject{

	private static final long serialVersionUID = 1L;
	private Long memberCode; // 会员号
	private Integer merchantType; // 会员类型 1个人会员，2企业会员
	private Integer serviceLevelCode; // 服务级别代码，外键对应t_service_level主键
	private String greeting; // 问候语
	private Integer status; // 会员状态0为创建，1正常状态（激活成功），2冻结状态，3.未激活，4删除状态
	private Integer securityQuestion; // 安全问题id
	private String securityAnswer;//安全问题答案
	private String ssoUserId;//与sso关联的用户id
	private Integer loginType;//登陆方式 1手机号，2Email
	private String loginName;//登录名
	private String loginPwd;//admin登录密码，其他操作员有其本身密码，个人会员除外
	private Date createDate;//数据创建时间
	private Date updateDate;//数据更新数据
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
	public Integer getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}
	public Integer getServiceLevelCode() {
		return serviceLevelCode;
	}
	public void setServiceLevelCode(Integer serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getSsoUserId() {
		return ssoUserId;
	}
	public void setSsoUserId(String ssoUserId) {
		this.ssoUserId = ssoUserId;
	}
	public Integer getLoginType() {
		return loginType;
	}
	public void setLoginType(Integer loginType) {
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