/**
 * 
 */
package com.pay.txncore.dto;

import javax.validation.constraints.NotNull;

/**
 * 账户支付用户验证DTO
 * @author huhb
 *
 */
public class AccountInfoValidateDTO {
	
	@NotNull
	private String userMark;
	
	@NotNull
	private String payPassword;
	
	private String operaterId;
	
	private String memberCode;
	
	private String partnerId;
	
	private String validateMsg;
	
	private boolean validateResult;
	
	private String time;
	
	private String leaveTime;
	
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getTime() {
		return time;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getUserMark() {
		return userMark;
	}

	public void setUserMark(String userMark) {
		this.userMark = userMark;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getValidateMsg() {
		return validateMsg;
	}

	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}

	public boolean isValidateResult() {
		return validateResult;
	}

	public void setValidateResult(boolean validateResult) {
		this.validateResult = validateResult;
	}

	public String getOperaterId() {
		return operaterId;
	}

	public void setOperaterId(String operaterId) {
		this.operaterId = operaterId;
	}

	@Override
	public String toString() {
		return "AccountInfoValidateDTO [leaveTime=" + leaveTime
				+ ", memberCode=" + memberCode + ", operaterId=" + operaterId
				+ ", partnerId=" + partnerId + ", payPassword=" + payPassword
				+ ", time=" + time + ", userMark=" + userMark
				+ ", validateMsg=" + validateMsg + ", validateResult="
				+ validateResult + "]";
	}
	
}
