/**
 *  File: SsoUserInfo.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-5   terry_ma     Create
 *
 */
package com.pay.app.facade.dto;



/**
 * 
 */
public class SsoUserInfo {

	private String message;
	private UserInfo[] result;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserInfo[] getResult() {
		return result;
	}
	public void setResult(UserInfo[] result) {
		this.result = result;
	}

}