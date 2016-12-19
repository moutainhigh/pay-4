/**
 *  File: MessageSendDTO.java
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
public class MessageSendDTO implements MutableDto {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 发件人
	 */
	private String sendId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 发件时间
	 */
	private Timestamp sendTime;
	/**
	 * 类型：普通 公告 订阅
	 */
	private Integer type;
	/**
	 * 状态：收藏 垃圾 删除
	 */
	private Integer status;
	/**
	 * messageContextID
	 */
	private Long messageContextId;
	/**
	 * 收件人ID
	 */
	private String toUserId;

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
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

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
