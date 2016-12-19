/** @Description 
 * @project 	poss-withdraw
 * @file 		HandBatchFileDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-19		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.dao.fileservice.impl;

import com.pay.fundout.withdraw.dao.fileservice.HandBatchFileDao;
import com.pay.inf.dao.impl.BaseDAOImpl;
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
public class HandBatchFileDaoImpl extends BaseDAOImpl<Object> implements
		HandBatchFileDao {

	@Override
	public int updateBatchFileInfo(BatchFileInfo batchFileInfo) {
		boolean count = this.update("schedule.fo-UpdateBatchFileInfo",
				batchFileInfo);
		return 2;
	}

	@Override
	public int updateBatchInfo(BatchInfo batchInfo) {
		this.update("schedule.fo-UpdateBatchInfo", batchInfo);
		return 2;
	}

}
