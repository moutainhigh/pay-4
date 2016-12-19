/**
 * 
 */
package com.pay.acc.commons;

/**
 * *余额方向  （1为加，2为减）
 * @author Administrator
 *
 */
public enum AmountByEnum {
	ADD(1,"加"),REDUCE(2,"减");
	private int code;
	private String message;
	
	private AmountByEnum(int code,String message){
		this.code=code;
		this.message=message;
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
