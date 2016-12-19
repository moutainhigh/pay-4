/**
 * 
 */
package com.pay.acc.service.member.dto;

import java.io.Serializable;

public class MemberInfoDto implements Serializable {

	/**
	 * 帐号
	 */
	private Long memberCode;
	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 用户类型 1个人会员，2企业会员
	 */
	private Integer memberType;
	/**
	 * 帐号类型
	 */
	//private String acctType;
	/**
	 * 用户名
	 */
	private String memberName;

	private String name;
	/**
	 * 用户级别代码
	 */
	private Integer levelCode;
	/**
	 * 状态
	 */
	private Integer status;

	private String mobile;

	private String email;

	/**
	 * 账户余额
	 */
	private Long balance;

	private String idNo;

	private String lastLoginTime;// 上一次登录时间

	// 0-未设置，1-已设置
	private Integer isSetupPayPwd = 0;

	private Integer securityQuestion;
	private String securityAnswer;

	private String loginPwd;

	private String payPwd;

	private String nickname;

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

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIsSetupPayPwd() {
		return isSetupPayPwd;
	}

	public void setIsSetupPayPwd(Integer isSetupPayPwd) {
		this.isSetupPayPwd = isSetupPayPwd;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
