package com.pay.pe.exception.account;

/**
 * g
 *
 */
public class SubjectManageException extends Exception {
	private String key;
	
	public String getKey() {
		return key;
	}

	public SubjectManageException(String key, String message) {
		super(message);
		this.key = key;
	}
	
	public SubjectManageException(String key, String message, Throwable e) {
		super(message, e);
		this.key = key;
	}
}
