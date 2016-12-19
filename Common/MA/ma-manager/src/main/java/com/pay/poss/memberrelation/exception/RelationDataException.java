package com.pay.poss.memberrelation.exception;

/**
 * 关联名单数据异常
 */
public class RelationDataException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9003234860038766577L;
	
	private static final String ERROR_MESSAGE = "关联名单数据异常";
	
	public RelationDataException() {
		super(ERROR_MESSAGE);
	}
	
	public RelationDataException(String message) {
		super(message);
	}
	
	public RelationDataException(String message,  Throwable cause) {
		super(message, cause);
	}
	
	public RelationDataException(Throwable t) {
		super(t);
	}
	
	public String toString() {
		return ERROR_MESSAGE;
	}
}
