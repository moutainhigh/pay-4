package com.pay.txncore.dto;


/**
 * 渠道授权交易返回结果
 * @author qiyun10
 *
 */
public class ChannelPreauthResult extends ChannelPaymentResult{
	private String authCode;
	private String authorStr;
	private String refNo;
	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getAuthorStr() {
		return authorStr;
	}
	public void setAuthorStr(String authorStr) {
		this.authorStr = authorStr;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	@Override
	public String toString() {
		return "ChannelPreauthResult [authCode=" + authCode + ", authorStr="
				+ authorStr + ", getErrorCode()=" + getErrorCode()
				+ ", getErrorMsg()=" + getErrorMsg() + ", getChannelOrderNo()="
				+ getChannelOrderNo() + ", getOrgCode()=" + getOrgCode()
				+ ", getPayAmount()=" + getPayAmount()
				+ ", getChannelReturnNo()=" + getChannelReturnNo()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
