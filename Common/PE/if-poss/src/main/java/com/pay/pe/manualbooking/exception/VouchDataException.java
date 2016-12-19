package com.pay.pe.manualbooking.exception;

/**
 * 手工记账数据异常
 */
public class VouchDataException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9003234860038766577L;
	
	private static final String ERROR_MESSAGE = "手工记账数据异常";
	
	public VouchDataException() {
		super(ERROR_MESSAGE);
	}
	
	public VouchDataException(String message) {
		super(message);
	}
	
	public VouchDataException(String message,  Throwable cause) {
		super(message, cause);
	}
	
	public VouchDataException(Throwable t) {
		super(t);
	}
	
	public String toString() {
		return ERROR_MESSAGE;
	}
}
