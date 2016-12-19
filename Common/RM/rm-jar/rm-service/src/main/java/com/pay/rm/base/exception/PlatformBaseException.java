package com.pay.rm.base.exception;

import com.pay.inf.exception.AppException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public abstract class PlatformBaseException extends AppException {
	private static final long serialVersionUID = -2731428447191391639L;
	protected ExceptionCodeEnum code = ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE;

	public PlatformBaseException(String msg, ExceptionCodeEnum code) {
		super(msg);
		this.code = code;
	}

	public PlatformBaseException(String msg, ExceptionCodeEnum code,
			Throwable cause) {
		super(msg, cause);
		this.code = code;
	}

	public ExceptionCodeEnum getCode() {
		return code;
	}

	public String toString() {
		return code.getCode() + ":" + code.getDescription() + "["
				+ getLocalizedMessage() + "]";
	}

}
