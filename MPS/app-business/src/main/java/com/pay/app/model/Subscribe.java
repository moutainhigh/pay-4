/**
 *  File: Subscribe.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.model;

import java.sql.Timestamp;

/**
 * 
 */
public class Subscribe implements Model{

	private Long id;
	private Integer type;
	private String context;
	private Timestamp creationDate;
	private Integer noticeMode;
	private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getNoticeMode() {
		return noticeMode;
	}
	public void setNoticeMode(Integer noticeMode) {
		this.noticeMode = noticeMode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}
}