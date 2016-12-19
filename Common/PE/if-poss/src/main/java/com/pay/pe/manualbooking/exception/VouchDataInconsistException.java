package com.pay.pe.manualbooking.exception;

/**
 * 手工记帐数据并发异常
 */
public class VouchDataInconsistException extends VouchDataException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2802450898193156316L;
	
	
	
	/**
	 * 当前手工记账申请状态
	 */
	private String presentVouchStatus;
	
	
	/**
	 * 期望手工记帐申请状态
	 */
	private String actualVouchStatus;
	
	public VouchDataInconsistException() {
		super();
	}
	
	public VouchDataInconsistException(String presentVouchStatus, String actualVouchStatus) {
		super();
		this.presentVouchStatus = presentVouchStatus;
		this.actualVouchStatus = actualVouchStatus;
	}
	
	public String toString() {
		return getMessage() + "\n present vouch status: " + presentVouchStatus +
			" and actual vouch status: " + actualVouchStatus;
	}
}
