package com.pay.fundout.withdraw.dto.autorisk;

import java.util.Date;

public class LoginFailLogDto {

	/*
	 * 商户号
	 */
	private Long memberCode;
	
	/*
	 * 登录错误时间
	 */
	private Date errorTime;

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}
	
}
