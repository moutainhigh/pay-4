package com.pay.pe.helper;

/**
 * 
 * @ClassName: TakenOnEnum
 * @Description: 1-收款方;2-付款方;
 * @author cf
 * @date Mar 8, 2012 3:22:37 PM
 * 
 */
public enum DependOnEnum {

	PAYER(1, "付款方"), PAYEE(2, "收款方");

	private int code;
	private String message;

	private DependOnEnum(int code, String message) {
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
