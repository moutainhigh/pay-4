/** @Description 
 * @project 	poss-withdraw
 * @file 		HandBatchFileDao.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-19		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.dao.fileservice;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-19
 * @see
 */
public interface HandBatchFileDao extends BaseDAO<Object> {
	/**
	 * 更新BatchInfo信息
	 * 
	 * @param batchInfo
	 * @return
	 */
	public int updateBatchInfo(BatchInfo batchInfo);

	/**
	 * 更新BatchFileInfo信息
	 * 
	 * @param batchFileInfo
	 * @return
	 */
	public int updateBatchFileInfo(BatchFileInfo batchFileInfo);

}
