/**
 * 
 */
package com.pay.acc.service.account.constantenum;

/**
 * @author Administrator
 * 
 */
public enum BalanceDirectEnum {
	BORROW(1, "借"), lEND(2, "代");
	private Integer code;
	private String message;

	private BalanceDirectEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Integer code) {
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
