package com.pay.txncore.dto;


/**
 * 预授权返回结果
 * @author peiyu.yang
 *
 */
public class PreauthResult extends PaymentResult{
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
    
 	
 	
}
