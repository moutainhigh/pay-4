/**
 * 
 */
package com.pay.acc.commons;

/**
 * @author Administrator
 *
 */
public enum AcctEnum {
	INVALID(0,"无效"),VALID(1,"有效");
	
	
	private int code;
	private String message;
	
	private AcctEnum(int code,String message){
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
