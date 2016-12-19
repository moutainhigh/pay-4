package com.pay.base.exception.matrixcard;

import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.inf.exception.AppException;


/**
 * 口令卡异常
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCardException extends AppException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ErrorCodeMatrixExceptionEnum errorEnum = ErrorCodeMatrixExceptionEnum.UN_EXPECTED_ERROR;

	public MatrixCardException() {
		super();
	}

	public MatrixCardException(String message, Object... arguments) {
		super(message, arguments);
	}

	public MatrixCardException(String message, Throwable cause) {
		super(message, cause);
	}

	public MatrixCardException(String message) {
		super(message);
	}

	public MatrixCardException(Throwable cause) {
		super(cause);
	}

	public MatrixCardException(ErrorCodeMatrixExceptionEnum errorEnum,
			String message, Throwable cause) {
		this(message, cause);
		this.errorEnum = errorEnum;
	}

	public MatrixCardException(ErrorCodeMatrixExceptionEnum errorEnum, String message) {
		this(message);
		this.errorEnum = errorEnum;
	}

	/**
	 * @return the errorEnum
	 */
	public ErrorCodeMatrixExceptionEnum getErrorEnum() {
		return errorEnum;
	}
}
