/**
 * 
 */
package com.pay.acc.exception;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.exception.AppException;

/**
 * @author jeffrey_teng 
 *
 * @date 2010-9-23
 */
public class MaAcctUpdateException extends AppException {

	private ErrorExceptionEnum errorEnum = ErrorExceptionEnum.UNKNOW_ERROR;

	/**
	 * 
	 */
	public MaAcctUpdateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param arguments
	 */
	public MaAcctUpdateException(String message, Object... arguments) {
		super(message, arguments);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MaAcctUpdateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MaAcctUpdateException(ErrorExceptionEnum errorEnum, String message, Throwable cause) {
		this(message, cause);
		this.errorEnum = errorEnum;
	}

	/**
	 * @param message
	 */
	public MaAcctUpdateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MaAcctUpdateException(ErrorExceptionEnum errorEnum, String message) {
		this(message);
		this.errorEnum = errorEnum;
	}

	/**
	 * @param cause
	 */
	public MaAcctUpdateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the errorEnum
	 */
	public ErrorExceptionEnum getErrorEnum() {
		return errorEnum;
	}
	

}
