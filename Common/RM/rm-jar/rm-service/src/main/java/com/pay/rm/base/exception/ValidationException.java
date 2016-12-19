package com.pay.rm.base.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 3296696807036316119L;

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}
}
