package com.pay.poss.base.exception;

import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

public class PossUntxException extends PlatformBaseException {
	private static final long serialVersionUID = -3074865314457508266L;

	public PossUntxException(String msg, ExceptionCodeEnum code) {
		super(msg, code);
	}

	public PossUntxException(String msg, ExceptionCodeEnum code, Throwable cause) {
		super(msg, code, cause);
	}

}
