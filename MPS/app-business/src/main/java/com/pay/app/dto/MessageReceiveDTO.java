/**
 *  File: MessageRecviveDTO.java
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
public class MessageReceiveDTO extends BaseDTO implements MutableDto {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 收件人
	 */
	private String sendName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 已读未读标记
	 */
	private Integer readFlag;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 读取时间
	 */
	private Timestamp readTime;
	/**
	 * 发件来时
	 */
	private Timestamp sendTime;
	/**
	 * messageContextID
	 */
	private Long messageContextId;
	/**
	 * 原userid改传membercode
	 */
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

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Long getMessageContextId() {
		return messageContextId;
	}

	public void setMessageContextId(Long messageContextId) {
		this.messageContextId = messageContextId;
	}

}
