/**
 *  File: MessageReceiveDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.dao.messagebox;

import java.util.List;

import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.model.MessageReceive;

/**
 * 
 */
public interface MessageReceiveDAO {

	/**
	 * 统计未读消息.
	 * 
	 * @param receveId
	 * @return
	 */
	int countUnRead(String userId);
	
	/**
	 * 查看个人所有站内信,根据userId
	 * @param userId
	 * @return
	 */
	List<MessageReceive> findMessageList(String userId);
	
	/**
	 * 根据userId来统计个人所有站内信的条数
	 * @param userId
	 * @return
	 */
	int countMessageReceive(String userId);
	
	/**
	 * 分页查看个人所以站内信
	 * @param messageReceiveDto
	 * @return
	 */
	List<MessageReceive> queryMessageListByPage(MessageReceiveDTO messageReceiveDto);
	
	/**
	 * 查询前5条的站内信
	 */
	List<MessageReceive> queryTopFMessage(Long memberCode);
	
	/**
	 * 更新一条站内信的读取状态
	 */
	void updateById(String id);
	
	/**
	 * 废弃/by lei.jiangl
	 * 查询当前MSG的上一条MSG
	 */
	MessageReceive queryOnMsg(Long id,String membercode);
	/**
	 * 废弃/by lei.jiangl
	 * 查询当前MSG的下一条MSG
	 */
	MessageReceive queryNextMsg(Long id,String membercode);
	
	/**
	 * 根据memberCode删除所有的站内信通知
	 */
	boolean cleanMessgaeReceive(String memberCode);
	
	
	int countById(String userId);
	
	MessageReceive queryByRn(Long rn,String membercode);
	
	
	List<MessageReceive> queryMessageReceiveByContextId(Long contextId,String memberCode);
}
