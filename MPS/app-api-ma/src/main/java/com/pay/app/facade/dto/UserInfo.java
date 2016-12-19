/**
 *  File: UserInfo.java
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
public class UserInfo {

	private String id;
	private String username;
	private String exists;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getExists() {
		return exists;
	}
	public void setExists(String exists) {
		this.exists = exists;
	}

}