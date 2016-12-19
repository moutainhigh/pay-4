package com.pay.acc.identityverify;

/**
 * 实名认证响应
 */
public class IdentityVerifyResDto {

	private String userId; // 合作商用户编号
	private String coopOrderNo; // 合作商原订单号
	private String auOrderNo; // 我司订单号
	private String auResultCode; // 验证结果状态
	private String auResultInfo; // 验证结果描述
	private String auSuccessTime; //

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCoopOrderNo() {
		return coopOrderNo;
	}

	public void setCoopOrderNo(String coopOrderNo) {
		this.coopOrderNo = coopOrderNo;
	}

	public String getAuOrderNo() {
		return auOrderNo;
	}

	public void setAuOrderNo(String auOrderNo) {
		this.auOrderNo = auOrderNo;
	}

	public String getAuResultCode() {
		return auResultCode;
	}

	public void setAuResultCode(String auResultCode) {
		this.auResultCode = auResultCode;
	}

	public String getAuResultInfo() {
		return auResultInfo;
	}

	public void setAuResultInfo(String auResultInfo) {
		this.auResultInfo = auResultInfo;
	}

	public String getAuSuccessTime() {
		return auSuccessTime;
	}

	public void setAuSuccessTime(String auSuccessTime) {
		this.auSuccessTime = auSuccessTime;
	}

}
