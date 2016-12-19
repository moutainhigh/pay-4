/**
 *  File: UserLogServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.user.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.app.common.userlogurl.UserLogType;
import com.pay.base.dao.user.UserLogDAO;
import com.pay.base.dto.UserLogDTO;
import com.pay.base.model.UserLog;
import com.pay.base.service.user.UserLogService;
import com.pay.inf.service.impl.BaseServiceImpl;

/**
 * 
 */
public class UserLogServiceImpl extends BaseServiceImpl implements
		UserLogService {

	public Class getDtoClass() {
		return UserLogDTO.class;
	}

	@Override
	public String findDateLineByMC(long memberCode) {
		return ((UserLogDAO) mainDao).findDateLineByMC(memberCode);
	}

	@Override
	public List findUserLogInfo(Timestamp beginTime, Timestamp endTime,
			String logType, String memberCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("logType", logType);
		map.put("memberCode", memberCode);
		return ((UserLogDAO) mainDao).findUserLogInfo(map);
	}

	/**
	 * 
	 * @param operatorIds
	 * @return
	 * @see com.pay.base.service.user.UserLogService#getOperatorEndLogInfo(java.util.List)
	 */
	public List<UserLog> getOperatorEndLogInfo(List<Long> operatorIds) {
		return ((UserLogDAO) mainDao).getOperatorEndLogInfo(operatorIds);
	}

	@Override
	public Map getAllLogType() {
		return UserLogType.getAllInfo();
	}

	/**
	 * 
	 * @param status
	 * @param type
	 * @param objectCode
	 * @return
	 * @see com.pay.base.service.user.UserLogService#findPossOperateObject(int,
	 *      int, java.lang.String)
	 */
	public List<String> findPossOperateObject(int status, int type,
			String objectCode) {
		if (StringUtils.isBlank(objectCode)) {
			return null;
		}
		return ((UserLogDAO) mainDao).findPossOperateObject(status, type,
				objectCode);
	}

	public int createPossOperateObject(String objectCode, String loginName,
			String loginIp, String actionUrl) {
		if (StringUtils.isBlank(objectCode) || StringUtils.isBlank(loginName)
				|| StringUtils.isBlank(loginName)
				|| StringUtils.isBlank(loginIp)
				|| StringUtils.isBlank(actionUrl)) {
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectCode", objectCode);
		map.put("loginName", loginName);
		map.put("loginIp", loginIp);
		map.put("actionUrl", actionUrl);
		map.put("type", Integer.valueOf(1));
		map.put("status", null);
		((UserLogDAO) mainDao).createPossOperateObject(map);
		return 1;
	}

	@Override
	public UserLog getLastLog(String loginName) {
		return ((UserLogDAO) mainDao).getLastLog(loginName);
	}

	@Override
	protected Class getModelClass() {
		return UserLog.class;
	}
}
