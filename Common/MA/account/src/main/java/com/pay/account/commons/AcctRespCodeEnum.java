package com.pay.account.commons;

public enum AcctRespCodeEnum {
     
	MEMBER_NOT_EXIST("0001","会员不存在"),
	;
	
	AcctRespCodeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
	
}
