package com.pay.acc.member.dto;

import java.io.Serializable;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-11-15 下午12:29:47
 */
public class ResultDto implements Serializable {

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
