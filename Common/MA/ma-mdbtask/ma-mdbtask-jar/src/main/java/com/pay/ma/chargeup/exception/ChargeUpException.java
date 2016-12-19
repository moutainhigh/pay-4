/**
 * 
 */
package com.pay.ma.chargeup.exception;

import com.pay.inf.exception.AppException;

/**
 * @author Administrator
 *
 */
public class ChargeUpException extends AppException {

	/**
	 * 
	 */
	public ChargeUpException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ChargeUpException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ChargeUpException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ChargeUpException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param arguments
	 */
	public ChargeUpException(String message, Object... arguments) {
		super(message, arguments);
		// TODO Auto-generated constructor stub
	}

}
