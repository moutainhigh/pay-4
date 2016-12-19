package com.pay.app.base.exception;

/**
 * This class wraps the exception thrown while doing conneting.
 * 
 * @Company woyo Corporation
 * @author terry_ma
 * @version 1.0
 */
public class TimeOutException extends Exception {
	private static final long serialVersionUID = -2066518567169549678L;

	/** Root cause of this nested exception */
	private Throwable cause;

	/**
	 * Construct a <code>AccountingException</code> with the specified detail
	 * message.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public TimeOutException(String msg) {
		super(msg);
	}

	/**
	 * Construct a <code>AccountingException</code> with the specified detail
	 * message and nested exception.
	 * 
	 * @param msg
	 *            the detail message
	 * @param ex
	 *            the nested exception
	 */
	public TimeOutException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

	/**
	 * Return the nested cause, or <code>null</code> if none.
	 */
	public Throwable getCause() {

		return (this.cause == this ? null : this.cause);
	}

	/**
	 * Return the detail message, including the message from the nested
	 * exception if there is one.
	 */
	public String getMessage() {
		if (getCause() == null) {
			return super.getMessage();
		} else {
			return super.getMessage() + "; nested exception is "
					+ getCause().getClass().getName() + ": "
					+ getCause().getMessage();
		}
	}
}
