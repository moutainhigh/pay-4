/**
 * 
 */
package com.pay.acc.commons;

/**
 * @author Administrator
 *
 */
public enum ChargeUpStatusEnum {
	CHARGEUP_NO_SEND_MQ(0,"未记账（未发mq）"),CHARGEUP(1,"已经记账"),CHARGEUP_FAIL(2,"记账失败"),CHARGEUP_SEND_MQ(3,"记账发mq");
	private Integer code;
	
	private String message;

	private ChargeUpStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
