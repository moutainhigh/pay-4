/** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileRecordDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-3		Henry.Zeng			Create 
 */
package com.pay.fundout.reconcile.dao.fileservice.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.reconcile.dao.fileservice.QueryReconcileRecordDao;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportRecord;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * <p>
 * 导入文件明细
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-3
 * @see
 */
public class QueryReconcileRecordDaoImpl extends BaseDAOImpl implements
		QueryReconcileRecordDao {

	protected Log logger = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.reconcile.dao.fileservice.impl.QueryReconcileRecordDao
	 * #insert(java.util.List)
	 */
	public List<Long> insert(List<ReconcileImportRecord> paramList)
			throws PlatformDaoException {

		List<Long> recordList = super.batchCreate(
				"RC_FS.fo_rc_createFileRecord", paramList);

		return recordList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.reconcile.dao.fileservice.impl.QueryReconcileRecordDao
	 * #query(com.pay.inf.dao.Page, java.lang.String)
	 */
	public Page<ReconcileImportRecord> query(
			final Page<ReconcileImportRecord> page,
			final Map<String, Object> params) throws PlatformDaoException {
		return super.findByQuery("RC_FS.fo_rc_findImportRecord2List", page,
				params);
	}

	public int callProc(Map<String, Object> params) throws PlatformDaoException {

		int resVal = 1;
		try {
			params = (Map<String, Object>) super.findObjectByCriteria(
					"RC_FS.fo_rc_param_ctl", params);
		} catch (PlatformDaoException e) {
			logger.error(
					this.getClass().getName() + "callProc:" + e.getMessage(), e);
			resVal = 0;
		}
		return resVal;

	}

	@Override
	public void deleteReconcileRecordInfo(Map<String, Object> params)
			throws PlatformDaoException {
		this.getSqlMapClientTemplate().delete(
				"RC_FS.fo_rc_deleteReconcileRecord", params);
	}

	@Override
	public void deleteReconcileResultInfo(Map<String, Object> params)
			throws PlatformDaoException {
		this.getSqlMapClientTemplate().delete(
				"RC_FS.fo_rc_deleteReconcileResult", params);
	}
}
