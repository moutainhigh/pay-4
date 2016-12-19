/**
 *  File: MessageRecvive.java
 *  Description:
 *  Copyright  2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.model;

import java.sql.Timestamp;

/**
 * 
 */
public class MessageReceive implements Model{

	private Long id;
	private String sendName;;
	private Integer readFlag;
	private Integer status;
	private Timestamp readTime;
	private Long messageContextId;
	private Timestamp sendTime;
	private String title;
	private String userId;
	private Long rn;
	
	public Long getRn() {
		return rn;
	}
	public void setRn(Long rn) {
		this.rn = rn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public Long getMessageContextId() {
		return messageContextId;
	}
	public void setMessageContextId(Long messageContextId) {
		this.messageContextId = messageContextId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public Integer getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getReadTime() {
		return readTime;
	}
	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}
	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}
}
