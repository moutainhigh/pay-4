/** @Description 
 * @project 	fo-securitycheck
 * @file 		DenyException.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.exception;

/**
 * <p>
 * 否决异常
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public class DeniedException extends RuntimeException {

	private static final long serialVersionUID = 9171540442021445417L;
	private String busiType;

	private String code;

	private String[] tips;

	public DeniedException(String msg, Throwable t) {
		super(msg, t);
	}

	public DeniedException(String code) {
		this.code = code;
	}

	public DeniedException(String busiType, String code, String[] tips) {
		this.busiType = busiType;
		this.code = code;
		this.tips = tips;

	}

	public String getCode() {
		return code;
	}

	public String[] getTips() {
		return tips;
	}

	public String getBusiType() {
		return busiType;
	}

}
