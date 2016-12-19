package com.pay.acc.service.member.dto;

import java.io.Serializable;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-11-15 上午11:20:31
 * 创建临时会员的返回值对象
 */
public class MemberCreateResult implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 会员号
	 */
	private Long memberCode;
	/**
	 * 账户号
	 */
	private String acctCode;
	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 8位随机密码
	 */
	private String tempAutoPwd;
	/**
	 * 姓名
	 */
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getTempAutoPwd() {
		return tempAutoPwd;
	}
	public void setTempAutoPwd(String tempAutoPwd) {
		this.tempAutoPwd = tempAutoPwd;
	}
}
