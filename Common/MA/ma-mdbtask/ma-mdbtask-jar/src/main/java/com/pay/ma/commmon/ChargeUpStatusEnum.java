/**
 * 
 */
package com.pay.ma.commmon;

/**
 * @author Administrator
 * 
 */
public enum ChargeUpStatusEnum {
	CHARGEUP_NO_SEND_MQ(0,"未记账（未发mq）"), SUCCESS(1, "记账成功"), FAIL(2, "记账失败"),CHARGEUP_SEND_MQ(3,"记账发mq");
	private int code;
	private String message;

	private ChargeUpStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
