package com.pay.acc.identityverify;

/**
 * 实名认证请求
 */
public class IdentityVerifyReqDto {

	private String userId; // 合作商编号
	private String coopOrderNo; // 合作商订单号
	private String auName; // 待验证的用户真实姓名
	private String auId; // 待验证的用户真实证件号
	private String reqDate; // 日期 yyyy-MM-dd hh:mm:ss
	private String ts; // 系统时间戳，单位毫秒
	private String sign; // 签名字符串

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

	public String getAuName() {
		return auName;
	}

	public void setAuName(String auName) {
		this.auName = auName;
	}

	public String getAuId() {
		return auId;
	}

	public void setAuId(String auId) {
		this.auId = auId;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
