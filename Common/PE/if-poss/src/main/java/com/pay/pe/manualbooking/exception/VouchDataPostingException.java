package com.pay.pe.manualbooking.exception;

/**
 * 手工记账申请数据记帐异常
 */
public class VouchDataPostingException extends VouchDataException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6833433766354326522L;

	public VouchDataPostingException() {
		super();
	}
	
	public VouchDataPostingException(String message) {
		super(message);
	}
	
	public VouchDataPostingException(String message, Exception e) {
		super(message, e);
	}
}
