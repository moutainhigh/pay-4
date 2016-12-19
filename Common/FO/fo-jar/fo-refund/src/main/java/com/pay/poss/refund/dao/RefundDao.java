 /** @Description 
 * @project 	poss-reconcile
 * @file 		RefundDao.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-09-01		Sunsea_Li		Create 
*/
package com.pay.poss.refund.dao;

import java.util.List;

import com.pay.poss.base.exception.PlatformDaoException;

public interface RefundDao {
	/**批量插入数据
	 * @param <T>
	 * @param stmtId:sqlId
	 * @param paramList:批量数据列表
	 * @return List<Long>：主键列表
	 * @throws PlatformDaoException
	 */
	public <T> List<Long> insertBatch(final String stmtId, final List<T> paramList) throws PlatformDaoException;
}
