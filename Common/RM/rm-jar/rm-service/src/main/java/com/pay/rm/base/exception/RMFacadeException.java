package com.pay.rm.base.exception;

import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public class RMFacadeException extends PlatformBaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6143728971659547203L;

	public RMFacadeException(String msg, ExceptionCodeEnum code) {
		super(msg, code);
	}

	public RMFacadeException(String msg, ExceptionCodeEnum code, Throwable cause) {
		super(msg, code, cause);
	}
}
