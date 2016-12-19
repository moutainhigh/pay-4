/**
 *  File: MessageSendServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox.impl;

import java.sql.Timestamp;
import java.util.List;

import com.pay.app.dao.messagebox.MessageSendDAO;
import com.pay.app.dto.MessageContextDTO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.dto.MessageSendDTO;
import com.pay.app.model.MessageSend;
import com.pay.app.service.messagebox.MessageContextService;
import com.pay.app.service.messagebox.MessageReceiveService;
import com.pay.app.service.messagebox.MessageSendService;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.util.BeanConvertUtil;

/**
 * 发送站内信服务 待重构
 */
public class MessageSendServiceImpl extends BaseServiceImpl implements
		MessageSendService {

	private MessageContextService messageContextService;
	private MessageReceiveService messageReceiveService;

	public void setMessageReceiveService(
			MessageReceiveService messageReceiveService) {
		this.messageReceiveService = messageReceiveService;
	}

	public void setMessageContextService(
			MessageContextService messageContextService) {
		this.messageContextService = messageContextService;
	}

	@SuppressWarnings("unchecked")
	public Class getDtoClass() {
		return MessageSendDTO.class;
	}

	@Override
	public MessageSendDTO sendMessage(MessageSendDTO messageSendDto,
			MessageContextDTO messageContextDto,
			MessageReceiveDTO messageReceiveDto) {
		// 优先保存内容，并将内容ID关联到messagesend messageReceive
		messageSendDto.setMessageContextId(messageContextService
				.messageContext(messageContextDto).getId());
		// messagesend保存
		MessageSend messageSend = BeanConvertUtil.convert(MessageSend.class,
				messageSendDto);
		Long id = (Long) mainDao.create(messageSend);
		messageSend.setId(id);
		// messageReceive保存
		messageReceiveDto.setReadFlag(0);
		messageReceiveDto.setStatus(1);
		messageReceiveDto.setSendName(messageSend.getSendId());
		messageReceiveDto
				.setReadTime(new Timestamp(System.currentTimeMillis()));
		messageReceiveDto.setTitle(messageSend.getTitle());
		messageReceiveDto
				.setMessageContextId(messageSend.getMessageContextId());
		messageReceiveDto.setSendTime(messageSend.getSendTime());
		messageReceiveDto.setUserId(messageSend.getToUserId());
		messageReceiveService.insertReceive(messageReceiveDto);
		return BeanConvertUtil.convert(MessageSendDTO.class, messageSend);
	}

	@Override
	public MessageSendDTO queryMessageSendByContextId(Long contextId) {
		List<MessageSend> list = ((MessageSendDAO) mainDao)
				.queryMessageSendByContextId(contextId);
		if (null != list && list.size() > 0) {
			return BeanConvertUtil.convert(MessageSendDTO.class, list.get(0));
		}
		return null;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return MessageSend.class;
	}

}
