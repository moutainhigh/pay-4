/**
 *  File: NotifyService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-25   terry_ma     Create
 *
 */
package com.pay.app.service.messagebox;

import com.pay.app.dto.NotifyMessageDto;

/**
 * 消息服务.
 */
public interface NotifyService {

	/**
	 * 站内信息发送
	 * 
	 * @param receiveId
	 *            接收者
	 * @param paraMap
	 *            站内信
	 */
	void sendAPPMsg(NotifyMessageDto notifyMessage);
}
