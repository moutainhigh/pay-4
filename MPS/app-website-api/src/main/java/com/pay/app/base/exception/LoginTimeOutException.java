/**
 *  File: LoginTimeOutException.java
 *  Description:
 *  Copyright 2006-2010 woyo Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2010-7-22   terry_ma     Create
 *
 */
package com.pay.app.base.exception;

import com.pay.inf.exception.AppException;

/**
 * 
 */
@SuppressWarnings("serial")
public class LoginTimeOutException extends AppException {

	/**
	 * Constructs an exception
	 * 
	 */
	public LoginTimeOutException() {
		super();
	}

	public LoginTimeOutException(String message) {
		super(message);
	}

	/**
	 * Constructs an exception with root cause
	 * 
	 * @param cause
	 *            The root cause
	 */
	public LoginTimeOutException(Throwable cause) {
		super(cause);
	}
}
