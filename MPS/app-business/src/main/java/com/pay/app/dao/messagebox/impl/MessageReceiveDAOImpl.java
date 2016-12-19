/**
 *  File: MessageReceiveDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.messagebox.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.app.dao.messagebox.MessageReceiveDAO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.model.MessageReceive;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class MessageReceiveDAOImpl extends BaseDAOImpl implements
		MessageReceiveDAO {

	public int countUnRead(String userId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("unReadCount"), userId);
	}

	@Override
	public int countById(String userId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("countById"), userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageReceive> findMessageList(String userId) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("findMessageList"), userId);
	}

	@Override
	public int countMessageReceive(String userId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryMessageCount"), userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageReceive> queryMessageListByPage(
			MessageReceiveDTO messageReceiveDto) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("queryMessageByPage"), messageReceiveDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageReceive> queryTopFMessage(Long memberCode) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("queryTopF"), memberCode);
	}

	@Override
	public void updateById(String id) {
		getSqlMapClientTemplate().update(namespace.concat("updateById"), id);
	}

	// 废弃/by lei.jiangl
	@Override
	public MessageReceive queryNextMsg(Long id, String membercode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", id);
		map.put("membercode", membercode);
		return (MessageReceive) getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryNextMsg"), map);
	}

	// 废弃/by lei.jiangl
	@Override
	public MessageReceive queryOnMsg(Long id, String membercode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", id);
		map.put("membercode", membercode);
		return (MessageReceive) getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryOnMsg"), map);
	}

	@Override
	public boolean cleanMessgaeReceive(String memberCode) {
		int result = getSqlMapClientTemplate().delete(
				namespace.concat("clean"), memberCode);
		return result >= 1;
	}

	@Override
	public MessageReceive queryByRn(Long rn, String membercode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rn", rn);
		map.put("membercode", membercode);
		return (MessageReceive) getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryByRn"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageReceive> queryMessageReceiveByContextId(Long contextId,
			String memberCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("contextId", contextId);
		map.put("memberCode", memberCode);
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("findMessageByContextId"), map);
	}

}
