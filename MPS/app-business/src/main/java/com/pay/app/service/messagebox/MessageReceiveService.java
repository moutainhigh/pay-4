/**
 *  File: MessageReceiveService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox;

import java.util.List;

import com.pay.app.dto.MessageReceiveDTO;
import com.pay.inf.service.BaseService;

/**
 * 个人站内信服务
 */
public interface MessageReceiveService extends BaseService {
	/**
	 * 统计未读消息,根据membercode
	 * 
	 * @param userId
	 * @return
	 */
	int countUnRead(String userId);

	/**
	 * 写入一条站内信通知
	 * 
	 * @param messageReceiveDto
	 * @return
	 */
	public MessageReceiveDTO insertReceive(MessageReceiveDTO messageReceiveDto);

	/**
	 * 删除一条站内信通知
	 * 
	 * @param id
	 * @return
	 */
	public boolean delMessageReceive(MessageReceiveDTO messageReceiveDto);

	/**
	 * 删除多条站内信通知
	 * 
	 * @param listId
	 * @return
	 */
	public int delMessageReceiveByList(List<MessageReceiveDTO> list);

	/**
	 * 根据memberCode删除所有的站内信通知
	 * 
	 * @param memberCode
	 * @return
	 */
	boolean cleanAllMessageReceive(String memberCode);

	/**
	 * 统计个人所有站内信的条数,根据membercode
	 * 
	 * @param userId
	 * @return
	 */
	int countMessageReceive(String userId);

	/**
	 * 分页查看个人所以站内信,根据membercode
	 * 
	 * @param messageReceiveDto
	 * @return
	 */
	List<MessageReceiveDTO> queryMessageListByPage(
			MessageReceiveDTO messageReceiveDto);

	/**
	 * 查询前5条的站内信
	 */
	List<MessageReceiveDTO> queryTopFMessage(Long memberCode);

	/**
	 * 更新一条站内信的读取状态
	 */
	void updateById(String id);

	/**
	 * 查询当前MSG的上一条MSG
	 */
	MessageReceiveDTO queryOnMsg(String id, String membercode);

	/**
	 * 查询当前MSG的下一条MSG
	 */
	MessageReceiveDTO queryNextMsg(String id, String membercode);

	/**
	 * 统计个人站内信数目
	 */
	int countByMemberCode(String MemberCode);

	/**
	 * 根据rownum查询
	 */
	MessageReceiveDTO queryByRn(Long rn, String membercode);

	MessageReceiveDTO queryMessageReceiveByContextId(Long contextId,
			String memberCode);

}
