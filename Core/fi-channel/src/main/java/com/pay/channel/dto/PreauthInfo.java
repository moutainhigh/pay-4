/**
 * 
 */
package com.pay.channel.dto;

/**
 * 跨境支付订单对象
 * 
 * @author chaoyue
 *
 */
public class PreauthInfo extends PaymentInfo{
	private String refNo;
	private String authCode;
	
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}
