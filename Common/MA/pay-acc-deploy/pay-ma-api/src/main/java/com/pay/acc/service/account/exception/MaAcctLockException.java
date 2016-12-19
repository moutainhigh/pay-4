/**
 * 
 */
package com.pay.acc.service.account.exception;

import com.pay.inf.exception.AppException;

/**
 * @author Administrator
 *
 */
public class MaAcctLockException extends AppException {

	/**
	 * 
	 */
	public MaAcctLockException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MaAcctLockException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MaAcctLockException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MaAcctLockException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param arguments
	 */
	public MaAcctLockException(String message, Object... arguments) {
		super(message, arguments);
		// TODO Auto-generated constructor stub
	}

}
