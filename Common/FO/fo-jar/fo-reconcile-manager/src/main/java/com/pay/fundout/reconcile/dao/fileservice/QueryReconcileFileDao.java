/** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileFileDao.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-2		Henry.Zeng			Create 
 */
package com.pay.fundout.reconcile.dao.fileservice;

import java.util.Map;

import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.WebQueryFile;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
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
public interface QueryReconcileFileDao extends BaseDAO {
	/**
	 * 查看上传银行对账文件 页面显示
	 * 
	 * @param webQueryFile
	 * @return
	 * @throws PlatformDaoException
	 */
	public Page<ReconcileImportFile> query(Page<ReconcileImportFile> page,
			WebQueryFile webQueryFile) throws PlatformDaoException;

	/**
	 * 废除文件
	 * 
	 * @param fileId
	 * @return
	 * @throws PlatformDaoException
	 */
	public int deleteByFileId(String fileId) throws PlatformDaoException;

	/**
	 * 验证文件名是否已经存在
	 * 
	 * @param file
	 * @return
	 * @throws PlatformDaoException
	 */
	boolean reconcileFileNameExist(ReconcileImportFile file)
			throws PlatformDaoException;

	/**
	 * 新增银行对账文件信息
	 * 
	 * @param file
	 * @throws PlatformDaoException
	 */
	void addReconcileFileInfo(ReconcileImportFile file)
			throws PlatformDaoException;

	/**
	 * 更新对账文件信息
	 * 
	 * @param file
	 * @throws PlatformDaoException
	 */
	void updateImportFile(ReconcileImportFile file) throws PlatformDaoException;

	/**
	 * 调用存储过程对账
	 * 
	 * @param params
	 * @return
	 * @throws PlatformDaoException
	 */
	Map<String, Object> executeReconcileInfo(Map<String, Object> params)
			throws PlatformDaoException;
}
