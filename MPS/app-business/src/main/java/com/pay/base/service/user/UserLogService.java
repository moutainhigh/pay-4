/**
 *  File: UserLogService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.user;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.pay.base.model.UserLog;
import com.pay.inf.service.BaseService;

/**
 * 
 */
public interface UserLogService extends BaseService {

	/**
	 * 跟据loginname来查找会员上次登录的时间
	 * 
	 * @param loginName
	 * @return
	 */
	UserLog getLastLog(final String loginName);

	/**
	 * 跟据会员信息来查找会员上次登录的时间
	 * 
	 * @param memberCode
	 * @return
	 */
	String findDateLineByMC(final long memberCode);

	/**
	 * 根据查询条件来查询日志信息
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param logType
	 * @param memberCode
	 * @return
	 */
	List findUserLogInfo(final Timestamp beginTime, final Timestamp endTime,
			final String logType, final String memberCode);

	Map getAllLogType();

	/**
	 * 根据操作员ID查询操作员最后登录信息
	 * 
	 * @param operatorIds
	 * @return
	 */
	public List<UserLog> getOperatorEndLogInfo(List<Long> operatorIds);

	/**
	 * 
	 * 根据操作对像(会员号或是账户号)和状态查询后台操作日志信息
	 * 
	 * @param status
	 * @param type
	 * @param objectCode
	 * @return
	 */
	public List<String> findPossOperateObject(int status, int type,
			String objectCode);

	/**
	 * 添加后台操作日志（主要是记录会员登录密码输错五次后锁会员状态）
	 * 
	 * @param objectCode
	 * @param loginName
	 * @param loginIp
	 * @param actionUrl
	 * @return
	 */
	public int createPossOperateObject(String objectCode, String loginName,
			String loginIp, String actionUrl);
}
