/**
 *  File: OperatorlogDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.operatorlog;



/**
 * 操作员日志DAO
 * @author zliner
 *
 */
public interface OperatorlogDao<E> {
	/**
	 * 保存相关操作员日志服务
	 * @param log           待保存操作员日志对象
	 */
	public void saveOperatorLog(E log);
}
