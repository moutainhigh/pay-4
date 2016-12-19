/**
 *  File: MessageContextService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox;

import com.pay.app.dto.MessageContextDTO;
import com.pay.inf.service.BaseService;

/**
 * 站内信内容服务
 */
public interface MessageContextService extends BaseService{
	/**
	 * 保存站内信内容
	 * @param messageContextDTO
	 * @return MessageContextDTO
	 */
	public MessageContextDTO messageContext(MessageContextDTO messageContextDto);
	/**
	 * 根据messageContextId删除内容
	 * @param messageContextId
	 * @return boolean
	 */
	public boolean delMessageContext(Long messageContextId);
	/**
	 * 根据ID查询内容
	 * @param id
	 * @return
	 */
	public MessageContextDTO findMessageContext(Long id);
}
