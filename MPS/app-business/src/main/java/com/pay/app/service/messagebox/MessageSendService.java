/**
 *  File: MessageSendService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox;

import com.pay.app.dto.MessageContextDTO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.dto.MessageSendDTO;
import com.pay.inf.service.BaseService;

/**
 * 发送站内信服务
 */
public interface MessageSendService extends BaseService {

	/**
	 * 发送站内信
	 * 
	 * @param messageSendDto
	 *            messageContextID不需设置
	 * @return
	 */
	public MessageSendDTO sendMessage(MessageSendDTO messageSendDto,
			MessageContextDTO messageContextDto,
			MessageReceiveDTO messageReceiveDto);

	/**
	 * @param contextID
	 * @return
	 */
	MessageSendDTO queryMessageSendByContextId(Long contextID);
}
