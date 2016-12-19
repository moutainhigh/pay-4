package com.pay.poss.base.exception;

import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

public class PossException extends PlatformBaseException {
	private static final long serialVersionUID = -5804700260985429408L;

	public PossException(String msg, ExceptionCodeEnum code) {
		super(msg, code);
	}

	public PossException(String msg, ExceptionCodeEnum code, Throwable cause) {
		super(msg, code, cause);
	}

}
