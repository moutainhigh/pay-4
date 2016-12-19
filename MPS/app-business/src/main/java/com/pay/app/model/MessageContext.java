/**
 *  File: MessageContext.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.model;

/**
 * 
 */
public class MessageContext implements Model{

	private Long id;
	private String context;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}
}