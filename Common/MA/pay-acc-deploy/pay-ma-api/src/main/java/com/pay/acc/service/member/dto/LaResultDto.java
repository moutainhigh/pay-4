package com.pay.acc.service.member.dto;

/** 
* @ClassName: LaResultDto 
* @Description: 利安查询返回对象
* @author cf
* @date 2011-9-26 上午09:12:59 
*  
*/

public class LaResultDto {
	private boolean resultFlag=false;   //是否成功
	private String  loginName;    		//被查询方在支付的会员登录名
	private String  balance;      		//会员账户余额
	private String 	errorCode;    		//错误代码
	private String  errorMsg;     		//错误信息
	private String  signMsg;			//签名信息
	
	
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	
	public boolean isResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
