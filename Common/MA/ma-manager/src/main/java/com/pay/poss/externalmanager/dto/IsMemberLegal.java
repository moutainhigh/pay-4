package com.pay.poss.externalmanager.dto;

public class IsMemberLegal {
	private Boolean returnBool;//true  false
	private String msg;//系统异常|商户异常|账户异常|账户正常
	
	public Boolean getReturnBool() {
		return returnBool;
	}
	public void setReturnBool(Boolean returnBool) {
		this.returnBool = returnBool;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
