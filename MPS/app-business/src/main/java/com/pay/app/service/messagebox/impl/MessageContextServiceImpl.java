/**
 *  File: MessageContextServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox.impl;

import com.pay.app.dto.MessageContextDTO;
import com.pay.app.model.MessageContext;
import com.pay.app.service.messagebox.MessageContextService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.util.BeanConvertUtil;

/**
 * 站内信内容服务
 */
public class MessageContextServiceImpl extends BaseServiceImpl implements
		MessageContextService {

	public Class getDtoClass() {
		return MessageContextDTO.class;
	}

	@Override
	public MessageContextDTO messageContext(MessageContextDTO messageContextDto) {

		MessageContext messageContext = BeanConvertUtil.convert(
				MessageContext.class, messageContextDto);

		Long id = (Long) mainDao.create(messageContext);
		messageContext.setId(id);

		return BeanConvertUtil.convert(MessageContextDTO.class, messageContext);
	}

	@Override
	public boolean delMessageContext(Long messageContextId) {
		boolean del = mainDao.delete(messageContextId);
		return del;
	}

	@Override
	public MessageContextDTO findMessageContext(Long id) {
		return BeanConvertUtil.convert(MessageContextDTO.class,
				mainDao.findById(id));
	}

	@Override
	protected Class getModelClass() {
		return MessageContext.class;
	}

}
