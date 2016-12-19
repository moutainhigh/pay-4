/**
 *  File: MessageSendDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.messagebox.impl;

import java.util.List;

import com.pay.app.dao.messagebox.MessageSendDAO;
import com.pay.app.model.MessageSend;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class MessageSendDAOImpl extends BaseDAOImpl implements MessageSendDAO {

	@Override
	public MessageSend sendMessage(MessageSend messageSend) {

		Long id = (Long) super.create(messageSend);
		messageSend.setId(id);
		return messageSend;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageSend> queryMessageSendByContextId(Long contextId) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("findByContextId"), contextId);
	}

}
