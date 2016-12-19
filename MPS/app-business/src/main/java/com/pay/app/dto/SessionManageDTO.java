/**
 *  File: SessionManageDTO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dto;

import java.sql.Timestamp;

import com.pay.inf.dto.MutableDto;

/**
 * 
 */
public class SessionManageDTO implements MutableDto {

	private Long id;
	private String userName;
	private Long rid;
	private Timestamp lastVisit;
	private Integer authenticated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Timestamp getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Timestamp lastVisit) {
		this.lastVisit = lastVisit;
	}

	public Integer getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Integer authenticated) {
		this.authenticated = authenticated;
	}

}
