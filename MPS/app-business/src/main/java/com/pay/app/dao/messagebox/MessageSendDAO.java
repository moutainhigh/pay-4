/**
 *  File: MessageSendDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.messagebox;

import java.util.List;

import com.pay.app.model.MessageSend;

/**
 * 
 */
public interface MessageSendDAO {
	
	public MessageSend sendMessage(MessageSend messageSend);
	
	List<MessageSend> queryMessageSendByContextId(Long contextId);
}