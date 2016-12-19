/**
 * 
 */
package com.pay.acc.exception;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.exception.AppException;

/**
 * @author wolf_huang
 * 
 * @date 2010-7-15
 */
public class MaMemberException extends AppException {
	private ErrorExceptionEnum errorEnum = ErrorExceptionEnum.UNKNOW_ERROR;

	/**
	 * 错误标识码
	 */

	public MaMemberException() {
		super();
	}

	public MaMemberException(String message, Object... arguments) {
		super(message, arguments);
	}

	public MaMemberException(String message, Throwable cause) {
		super(message, cause);
	}

	public MaMemberException(String message) {
		super(message);
	}

	public MaMemberException(Throwable cause) {
		super(cause);
	}

	public MaMemberException(ErrorExceptionEnum errorEnum, String message,
			Throwable cause) {
		this(message, cause);
		this.errorEnum = errorEnum;
	}

	public MaMemberException(ErrorExceptionEnum errorEnum, String message) {
		this(message);
		this.errorEnum = errorEnum;
	}

	/**
	 * @return the errorEnum
	 */
	public ErrorExceptionEnum getErrorEnum() {
		return errorEnum;
	}

}
