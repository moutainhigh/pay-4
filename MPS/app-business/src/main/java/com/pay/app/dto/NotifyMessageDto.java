/**
 *  File: NotifyMessageDto.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-25   terry_ma     Create
 *
 */
package com.pay.app.dto;

import java.io.Serializable;

/**
 * 消息通知对象.
 */
public class NotifyMessageDto implements Serializable {

	/**
	 * 收件人ID
	 */
	private String receiveId;
	/**
	 * 站内信主题
	 */
	private String title;
	/**
	 * 站内信内容
	 */
	private String context;

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
