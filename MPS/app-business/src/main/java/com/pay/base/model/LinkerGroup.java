/**
 *  File: LinkerGroup.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.model;

import com.pay.app.model.Model;

/**
 * 
 */
public class LinkerGroup implements Model{

	private Long id;
	private String groupName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}
}