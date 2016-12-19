package com.pay.poss.base.exception;

import org.springframework.dao.DataAccessException;

import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public class PlatformDaoException extends DataAccessException {
	private static final long serialVersionUID = -7807814181386098949L;

	private ExceptionCodeEnum code = ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE;

	public PlatformDaoException(String msg, ExceptionCodeEnum code) {
		super(msg);
		this.code = code;
	}

	public PlatformDaoException(String msg, ExceptionCodeEnum code, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}

	public ExceptionCodeEnum getCode() {
		return code;
	}

	public String toString() {
		return code.getCode() + ":" + code.getDescription() + "[" + getLocalizedMessage() + "]";
	}
}
