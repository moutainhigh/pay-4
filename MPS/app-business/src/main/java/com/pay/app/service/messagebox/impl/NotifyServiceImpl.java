/**
 *  File: NotifyServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-25   terry_ma     Create
 *
 */
package com.pay.app.service.messagebox.impl;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.dto.MessageContextDTO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.dto.MessageSendDTO;
import com.pay.app.dto.NotifyMessageDto;
import com.pay.app.service.messagebox.MessageSendService;
import com.pay.app.service.messagebox.NotifyService;

/**
 * 发送消息.
 */
public class NotifyServiceImpl implements NotifyService {

	private static final int COLLECTION = 1;
	private static final int UNREAD = 0;
	private static final String APP_FLAG = "系统管理员";
	private MessageSendService messageSendService;
	private Log logger = LogFactory.getLog(NotifyServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.messagebox.NotifyService#sendAPPMsg(java.lang
	 * .String, java.util.Map)
	 */
	@Override
	public void sendAPPMsg(NotifyMessageDto notifyMessage) {

		// 发送站内信
		MessageSendDTO sendDto = new MessageSendDTO();
		MessageContextDTO contextDto = new MessageContextDTO();
		MessageReceiveDTO receivedDto = new MessageReceiveDTO();

		receivedDto.setReadFlag(UNREAD);
		receivedDto.setSendTime(new Timestamp(System.currentTimeMillis()));
		receivedDto.setStatus(COLLECTION);
		receivedDto.setUserId(notifyMessage.getReceiveId());
		receivedDto.setReadTime(new Timestamp(System.currentTimeMillis()));

		sendDto.setSendId(APP_FLAG);
		sendDto.setTitle(notifyMessage.getTitle());
		sendDto.setStatus(COLLECTION);
		sendDto.setType(1);
		sendDto.setSendTime(new Timestamp(System.currentTimeMillis()));
		sendDto.setToUserId(notifyMessage.getReceiveId());

		contextDto.setContext(notifyMessage.getContext());
		try{
			messageSendService.sendMessage(sendDto, contextDto, receivedDto);
		}catch(Exception e){
			logger.error("send message exception:", e);
		}
		
	}

	public void setMessageSendService(MessageSendService messageSendService) {
		this.messageSendService = messageSendService;
	}

}
