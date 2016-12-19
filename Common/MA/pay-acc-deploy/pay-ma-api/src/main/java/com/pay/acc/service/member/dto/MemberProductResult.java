package com.pay.acc.service.member.dto;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;

public class MemberProductResult {

	/**
	 * 返回结果 true为通过 false为不通过
	 */
	private boolean returnBool=false;
	/**
	 * 返回的错误信息
	 */
	private String errMsg;
	/**
	 * 返回的错误代码
	 */
	private String errCode;
	/**
	 * 返回的接收端 需要的对象
	 */
	private Object returnObj;
	public boolean isReturnBool() {
		return returnBool;
	}
	public void setReturnBool(boolean returnBool) {
		this.returnBool = returnBool;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public Object getReturnObj() {
		return returnObj;
	}
	public void setReturnObj(Object returnObj) {
		this.returnObj = returnObj;
	}
	
	
	public void setError(ErrorExceptionEnum ee){
		this.setErrCode(ee.getErrorCode());
		this.setErrMsg(ee.getMessage());
	}
	
}
