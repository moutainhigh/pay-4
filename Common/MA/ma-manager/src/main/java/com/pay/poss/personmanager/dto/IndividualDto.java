package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.util.Date;

public class IndividualDto implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Long 	memberCode;
	private String 	loginName;// 登陆名
	private String 	email;
	private String 	mobile;// 手机号码
	private String 	userName;// 真实姓名
	private Long 	serviceLevelCode;// 会员等级
	private Long 	acctStatus;// 账户状态
	private String 	tel;// 固定电话
	private Date 	lastLoginDate;// 最近一次登陆时间
	private String 	lastLoginIp;// 最近一次登陆IP
	private String 	sosUserId;// pay社区账户

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getServiceLevelCode() {
		return serviceLevelCode;
	}

	public void setServiceLevelCode(Long serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}

	public Long getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(Long acctStatus) {
		this.acctStatus = acctStatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getSosUserId() {
		return sosUserId;
	}

	public void setSosUserId(String sosUserId) {
		this.sosUserId = sosUserId;
	}

}
