/**
 *  File: UserLogDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dao.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.dao.user.UserLogDAO;
import com.pay.base.model.UserLog;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 用户操作日志DAO
 */
public class UserLogDAOImpl extends BaseDAOImpl implements UserLogDAO {

	/**
	 * 跟据会员信息来查找会员上次登录的时间
	 * 
	 * @param memberCode
	 * @return
	 */
	public String findDateLineByMC(final long memberCode) {
		return (String) getSqlMapClientTemplate().queryForObject(
				namespace.concat("findDatelineByMC"), memberCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLog> findUserLogInfo(Map<Object, Object> map) {
		return (List<UserLog>) getSqlMapClientTemplate().queryForList(
				namespace.concat("findUserLogInfoList"), map);
	}

	/**
	 * 
	 * @param operatorIds
	 * @return
	 * @see com.pay.base.dao.user.UserLogDAO#getOperatorEndLogInfo(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<UserLog> getOperatorEndLogInfo(List<Long> operatorIds) {
		return (List<UserLog>) getSqlMapClientTemplate().queryForList(
				namespace.concat("getOperatorEndLogInfo"), operatorIds);
	}

	/**
	 * 
	 * @param status
	 * @param type
	 * @param objectCode
	 * @return
	 * @see com.pay.base.dao.user.UserLogDAO#findPossOperateObject(int,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> findPossOperateObject(int status, int type,
			String objectCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("type", type);
		map.put("objectCode", objectCode);
		return (List<String>) getSqlMapClientTemplate().queryForList(
				namespace.concat("findPossOperateObject"), map);
	}

	/**
	 * 添加后台操作日志（主要是记录会员登录密码输错五次后锁会员状态）
	 * 
	 * @param map
	 */
	public void createPossOperateObject(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		getSqlMapClientTemplate().insert(namespace.concat("createPossOperate"),
				map);
	}

	@Override
	public UserLog getLastLog(String loginName) {
		return (UserLog) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getLastLog"), loginName);
	}
}
