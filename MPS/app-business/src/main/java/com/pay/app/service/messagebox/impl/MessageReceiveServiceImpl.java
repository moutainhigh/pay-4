/**
 *  File: MessageReceiveServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.app.dao.messagebox.MessageReceiveDAO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.model.MessageReceive;
import com.pay.app.service.messagebox.MessageContextService;
import com.pay.app.service.messagebox.MessageReceiveService;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class MessageReceiveServiceImpl extends BaseServiceImpl implements
		MessageReceiveService {

	private MessageContextService messageContextService;

	public void setMessageContextService(
			MessageContextService messageContextService) {
		this.messageContextService = messageContextService;
	}

	@SuppressWarnings("unchecked")
	public Class getDtoClass() {
		return MessageReceiveDTO.class;
	}

	@Override
	public int countUnRead(String userId) {
		return ((MessageReceiveDAO) mainDao).countUnRead(userId);
	}

	@Override
	public MessageReceiveDTO insertReceive(MessageReceiveDTO messageReceiveDto) {
		MessageReceive messageReceive = BeanConvertUtil.convert(
				MessageReceive.class, messageReceiveDto);

		Long id = (Long) mainDao.create(messageReceive);
		messageReceive.setId(id);
		return BeanConvertUtil.convert(MessageReceiveDTO.class, messageReceive);
	}

	@Override
	public boolean delMessageReceive(MessageReceiveDTO messageReceiveDto) {
		// 删除收件&同时删除messagecontext
		boolean del = mainDao.delete(messageReceiveDto.getId());
		messageContextService.delMessageContext(messageReceiveDto
				.getMessageContextId());
		return del;
	}

	@Override
	public int delMessageReceiveByList(
			List<MessageReceiveDTO> messageReceiveDtoList) {
		int num = 0;
		if (messageReceiveDtoList.size() > 0) {
			for (MessageReceiveDTO messageReceiveDTO : messageReceiveDtoList) {
				this.delMessageReceive(messageReceiveDTO);
				num++;
			}
		}
		return num;
	}

	@Override
	public boolean cleanAllMessageReceive(String memberCode) {
		// 删除收件的站内信的同时删除messageContext
		boolean del = ((MessageReceiveDAO) mainDao)
				.cleanMessgaeReceive(memberCode);
		return del;
	}

	@Override
	public int countMessageReceive(String userId) {
		return ((MessageReceiveDAO) mainDao).countMessageReceive(userId);
	}

	@Override
	public List<MessageReceiveDTO> queryMessageListByPage(
			MessageReceiveDTO messageReceiveDto) {
		List<MessageReceive> list = ((MessageReceiveDAO) mainDao)
				.queryMessageListByPage(messageReceiveDto);
		List<MessageReceiveDTO> listdto = new ArrayList<MessageReceiveDTO>();
		for (MessageReceive messageReceive : list) {
			MessageReceiveDTO dto = BeanConvertUtil.convert(
					MessageReceiveDTO.class, messageReceive);
			listdto.add(dto);
		}
		return listdto;
	}

	@Override
	public List<MessageReceiveDTO> queryTopFMessage(Long memberCode) {
		List<MessageReceive> list = ((MessageReceiveDAO) mainDao)
				.queryTopFMessage(memberCode);
		List<MessageReceiveDTO> listdto = new ArrayList<MessageReceiveDTO>();
		for (MessageReceive messageReceive : list) {
			MessageReceiveDTO dto = BeanConvertUtil.convert(
					MessageReceiveDTO.class, messageReceive);
			listdto.add(dto);
		}
		return listdto;
	}

	@Override
	public void updateById(String id) {
		((MessageReceiveDAO) mainDao).updateById(id);
	}

	@Override
	public MessageReceiveDTO queryNextMsg(String id, String membercode) {
		MessageReceive mr = ((MessageReceiveDAO) mainDao).queryNextMsg(
				new Long(id), membercode);
		MessageReceiveDTO dto = BeanConvertUtil.convert(
				MessageReceiveDTO.class, mr);
		return dto;
	}

	@Override
	public MessageReceiveDTO queryOnMsg(String id, String membercode) {
		MessageReceive mr = ((MessageReceiveDAO) mainDao).queryOnMsg(new Long(
				id), membercode);
		MessageReceiveDTO dto = BeanConvertUtil.convert(
				MessageReceiveDTO.class, mr);
		return dto;
	}

	@Override
	public int countByMemberCode(String MemberCode) {
		return ((MessageReceiveDAO) mainDao).countById(MemberCode);
	}

	@Override
	public MessageReceiveDTO queryByRn(Long rn, String membercode) {
		MessageReceive mr = ((MessageReceiveDAO) mainDao).queryByRn(rn,
				membercode);
		return BeanConvertUtil.convert(MessageReceiveDTO.class, mr);
	}

	@Override
	public MessageReceiveDTO queryMessageReceiveByContextId(Long contextId,
			String memberCode) {
		List<MessageReceive> list = ((MessageReceiveDAO) mainDao)
				.queryMessageReceiveByContextId(contextId, memberCode);
		if (null != list && list.size() > 0) {
			return BeanConvertUtil
					.convert(MessageReceiveDTO.class, list.get(0));
		}
		return null;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return MessageReceive.class;
	}

}
