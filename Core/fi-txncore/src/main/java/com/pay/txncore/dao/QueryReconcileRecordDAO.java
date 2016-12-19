/** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileRecordDao.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-3		Henry.Zeng			Create 
 */
package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.base.exception.PlatformDaoException;
import com.pay.txncore.model.ReconcileImportRecord;

/**
 * <p>
 * 查询文件明细
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-3
 * @see
 */
public interface QueryReconcileRecordDAO {

	public abstract List<Long> insert(List<ReconcileImportRecord> paramList)
			throws PlatformDaoException;

	public abstract Page<ReconcileImportRecord> query(
			final Page<ReconcileImportRecord> page,
			final Map<String, Object> params) throws PlatformDaoException;

	/**
	 * 删除对账明细信息
	 * 
	 * @param Map
	 *            <String,Object>
	 * @throws PlatformDaoException
	 */
	void deleteReconcileRecordInfo(Map<String, Object> params)
			throws PlatformDaoException;

	/**
	 * 删除对账结果信息
	 * 
	 * @param Map
	 *            <String,Object>
	 * @throws PlatformDaoException
	 */
	void deleteReconcileResultInfo(Map<String, Object> params)
			throws PlatformDaoException;
}