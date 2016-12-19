/**
 * ConnectException.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.exception;

/**
 * latest modified time : 2011-8-23 上午10:02:45
 * @author bigknife
 */
public class ConnectException extends Exception {

	/**
	 * 
	 */
	public ConnectException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ConnectException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ConnectException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConnectException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
