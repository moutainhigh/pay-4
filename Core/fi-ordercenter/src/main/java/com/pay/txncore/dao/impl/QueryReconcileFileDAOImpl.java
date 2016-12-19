/** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileFileDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-2		Henry.Zeng			Create 
 */
package com.pay.txncore.dao.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.base.exception.PlatformDaoException;
import com.pay.txncore.dao.QueryReconcileFileDAO;
import com.pay.txncore.model.ReconcileImportFile;
import com.pay.txncore.model.WebQueryFile;

/**
 * <p>
 * 查询文件上传信息
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-2
 * @see
 */
public class QueryReconcileFileDAOImpl extends BaseDAOImpl implements
		QueryReconcileFileDAO {

	@Override
	public int deleteByFileId(final String fileId) throws PlatformDaoException {

		return this.getSqlMapClientTemplate().delete(
				"fo_rc_deleteImportFileById", fileId);
	}

	@Override
	public Page<ReconcileImportFile> query(
			final Page<ReconcileImportFile> page,
			final WebQueryFile webQueryFile) throws PlatformDaoException {
		return this
				.findByQuery("fo_rc_findImportFile2List", page, webQueryFile);
	}

	@Override
	public boolean reconcileFileNameExist(ReconcileImportFile file)
			throws PlatformDaoException {
		Integer count = (Integer) this.findObjectByCriteria(
				"fo_rc_validFileInfoExist", file);

		if (null != count && 0 < count.intValue()) {
			return true;
		}
		return false;
	}

	@Override
	public void addReconcileFileInfo(ReconcileImportFile file)
			throws PlatformDaoException {
		this.create("createFile", file);
	}

	@Override
	public void updateImportFile(ReconcileImportFile file)
			throws PlatformDaoException {
		this.update("fo_rc_updateImportFile", file);
	}

	@Override
	public Map<String, Object> executeReconcileInfo(Map<String, Object> params)
			throws PlatformDaoException {
		this.getSqlMapClientTemplate().queryForObject("fo_rc_proc_ctl", params);
		return params;
	}

}
