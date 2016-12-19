/**
 * 
 */
package com.pay.txncore.dto;

/**
 * 渠道返回对象
 * @author chaoyue
 *
 */
public class ChannelPreauthResponseDto extends ChannelResponseDto{
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
		return "ChannelPreauthResponseDto [authCode=" + authCode
				+ ", authorStr=" + authorStr + ", refNo=" + refNo
				+ ", getChannelOrderNo()=" + getChannelOrderNo()
				+ ", getResponseCode()=" + getResponseCode()
				+ ", getResponseDesc()=" + getResponseDesc()
				+ ", getChannelReturnNo()=" + getChannelReturnNo()
				+ ", getOrgCode()=" + getOrgCode() + ", getPayAmount()="
				+ getPayAmount() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
