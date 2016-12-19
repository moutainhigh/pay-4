/** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileFileDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-2		Henry.Zeng			Create 
 */
package com.pay.fundout.reconcile.dao.fileservice.impl;

import java.util.Map;

import com.pay.fundout.reconcile.dao.fileservice.QueryReconcileFileDao;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.WebQueryFile;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * <p>
 * 查询文件上传信息
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-2
 * @see
 */
public class QueryReconcileFileDaoImpl extends BaseDAOImpl implements
		QueryReconcileFileDao {

	@Override
	public int deleteByFileId(final String fileId) throws PlatformDaoException {

		return this.getSqlMapClientTemplate().delete(
				"RC_FS.fo_rc_deleteImportFileById", fileId);
	}

	@Override
	public Page<ReconcileImportFile> query(
			final Page<ReconcileImportFile> page,
			final WebQueryFile webQueryFile) throws PlatformDaoException {
		return this.findByQuery("RC_FS.fo_rc_findImportFile2List", page,
				webQueryFile);
	}

	@Override
	public boolean reconcileFileNameExist(ReconcileImportFile file)
			throws PlatformDaoException {
		Integer count = (Integer) this.findObjectByCriteria(
				"RC_FS.fo_rc_validFileInfoExist", file);

		if (null != count && 0 < count.intValue()) {
			return true;
		}
		return false;
	}

	@Override
	public void addReconcileFileInfo(ReconcileImportFile file)
			throws PlatformDaoException {
		this.create("RC_FS.fo_rc_createImportFile", file);
	}

	@Override
	public void updateImportFile(ReconcileImportFile file)
			throws PlatformDaoException {
		this.update("RC_FS.fo_rc_updateImportFile", file);
	}

	@Override
	public Map<String, Object> executeReconcileInfo(Map<String, Object> params)
			throws PlatformDaoException {
		this.getSqlMapClientTemplate().queryForObject("RC_FS.fo_rc_proc_ctl",
				params);
		return params;
	}

}
