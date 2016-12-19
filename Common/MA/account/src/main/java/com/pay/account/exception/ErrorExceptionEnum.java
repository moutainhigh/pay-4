/**
 * 
 */
package com.pay.account.exception;

/**
 * @author chaoyue
 *
 */
public enum ErrorExceptionEnum {
	
	MEMBER_NON_EXIST_ERROR("1001","会员不存在"),

	NOT_MATCHING_AMOUNT("1002","成功金额与订单金额不匹配"),
	
	CHARGE_FAIL("1003","充值失败"),
	
	WITHDRAW_FAIL("1004","提现失败"),
	
	PARAMETER_ERROR("1005","参数校验失败"),
	
	UNKNOW_ERROR("9999","未知异常"),
	
	BANK_ACCT_NOT_EXISTS("3001","银行卡不存在"),
	
	;
	
	private String responseCode;
	private String responseDesc;
	
	private ErrorExceptionEnum(String responseCode,String responseDesc){
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}
	
}
