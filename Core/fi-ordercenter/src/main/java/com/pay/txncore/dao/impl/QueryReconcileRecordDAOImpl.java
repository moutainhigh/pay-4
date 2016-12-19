/** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileRecordDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-3		Henry.Zeng			Create 
 */
package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.base.exception.PlatformDaoException;
import com.pay.txncore.dao.QueryReconcileRecordDAO;
import com.pay.txncore.model.ReconcileImportRecord;

/**
 * <p>
 * 导入文件明细
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-3
 * @see
 */
public class QueryReconcileRecordDAOImpl extends BaseDAOImpl implements
		QueryReconcileRecordDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.reconcile.dao.fileservice.impl.QueryReconcileRecordDao
	 * #insert(java.util.List)
	 */
	public List<Long> insert(List<ReconcileImportRecord> paramList)
			throws PlatformDaoException {

		List<Long> recordList = super.batchCreate("createRecord", paramList);

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
		return super.findByQuery("fo_rc_findImportRecord2List", page, params);
	}

	public int callProc(Map<String, Object> params) throws PlatformDaoException {

		int resVal = 1;
		try {
			params = (Map<String, Object>) super.findObjectByCriteria(
					"fo_rc_param_ctl", params);
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
		this.getSqlMapClientTemplate().delete("fo_rc_deleteReconcileRecord",
				params);
	}

	@Override
	public void deleteReconcileResultInfo(Map<String, Object> params)
			throws PlatformDaoException {
		this.getSqlMapClientTemplate().delete("fo_rc_deleteReconcileResult",
				params);
	}
}
