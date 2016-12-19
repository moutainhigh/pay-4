package com.pay.pe.manualbooking.exception;

/**
 * 手工记帐构建异常
 */
public class VouchDataBuildException extends VouchDataException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5056945168197825573L;

	public VouchDataBuildException(String message, Throwable t) {
		super(message, t);
	}
	
	public VouchDataBuildException(String message) {
		super(message);
	}
	
	public VouchDataBuildException(Throwable t) {
		super(t);
	}
}
