package com.pay.app.base.api.common.enums;

public enum TokenEnum {
	TEMPORARY_REG("temporary_reg_code","个人临时用户注册"),
	PAY_CHAIN("paychain_generate_code","生成支付链收款");
	
	private String code;
	private String message;
	
	
	private TokenEnum(String code,String message){
		this.code=code;
		this.message=message;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
