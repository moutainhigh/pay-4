/**
 * 
 */
package com.pay.acc.exception;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.exception.AppUnTxException;

/**
 * @author wolf_huang 
 *
 * @date 2010-7-15
 */
public class MaMemberQueryException extends AppUnTxException {
	
	private ErrorExceptionEnum errorEnum = ErrorExceptionEnum.UNKNOW_ERROR;

	public MaMemberQueryException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MaMemberQueryException(String message, Object... arguments) {
		super(message, arguments);
		// TODO Auto-generated constructor stub
	}

	public MaMemberQueryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	


	public MaMemberQueryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MaMemberQueryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the errorEnum
	 */
	public ErrorExceptionEnum getErrorEnum() {
		return errorEnum;
	}
	
	public MaMemberQueryException(ErrorExceptionEnum errorEnum,String message,Throwable cause){
		this(message,cause);
		this.errorEnum=errorEnum;
	}
	
	
	public MaMemberQueryException(ErrorExceptionEnum errorEnum,String message){
		this(message);
		this.errorEnum=errorEnum;
	}

	
}
