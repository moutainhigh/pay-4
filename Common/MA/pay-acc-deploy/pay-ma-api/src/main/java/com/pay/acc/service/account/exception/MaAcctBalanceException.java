/**
 * 
 */
package com.pay.acc.service.account.exception;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.exception.AppException;

/**
 * @author wolf_huang 
 * 
 * @date 2010-9-21
 */
@SuppressWarnings("serial")
public class MaAcctBalanceException extends AppException {

	private ErrorExceptionEnum errorEnum = ErrorExceptionEnum.UNKNOW_ERROR;

	/**
	 * 
	 */
	public MaAcctBalanceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param arguments
	 */
	public MaAcctBalanceException(String message, Object... arguments) {
		super(message, arguments);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MaAcctBalanceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MaAcctBalanceException(ErrorExceptionEnum errorEnum, String message, Throwable cause) {
		this(message, cause);
		this.errorEnum = errorEnum;
	}

	/**
	 * @param message
	 */
	public MaAcctBalanceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MaAcctBalanceException(ErrorExceptionEnum errorEnum, String message) {
		this(message);
		this.errorEnum = errorEnum;
	}

	/**
	 * @param cause
	 */
	public MaAcctBalanceException(Throwable cause) {
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
