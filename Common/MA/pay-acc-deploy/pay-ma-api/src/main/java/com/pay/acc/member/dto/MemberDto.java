package com.pay.acc.member.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long memberCode;
	private Integer type;
	private Integer serviceLevelCode;
	private String greeting;
	private Integer status;
	private Integer securityQuestion;
	private String securityAnswer;
	private String ssoUserId;
	private Integer loginType;
	private String loginName;
	private String loginPwd;
	private Date createDate;
	private Date updateDate;
	private String lastLoginTime;

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "Member [createDate=" + createDate + ", greeting=" + greeting
				+ ", loginName=" + loginName + ", loginPwd=" + loginPwd
				+ ", loginType=" + loginType + ", memberCode=" + memberCode
				+ ", securityAnswer=" + securityAnswer + ", securityQuestion="
				+ securityQuestion + ", serviceLevelCode=" + serviceLevelCode
				+ ", ssoUserId=" + ssoUserId + ", status=" + status + ", type="
				+ type + ", updateDate=" + updateDate + "]";
	}

}