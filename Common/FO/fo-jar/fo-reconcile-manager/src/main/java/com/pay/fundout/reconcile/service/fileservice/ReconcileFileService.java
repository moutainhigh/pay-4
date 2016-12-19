/**
 *  File: ReconcileFileService.java
 *  Description:对账文件管理接口定义
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-17    jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.reconcile.service.fileservice;

import java.io.InputStream;
import java.util.Map;

import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportRecord;
import com.pay.fundout.reconcile.model.fileservice.WebQueryFile;
import com.pay.inf.dao.Page;

public interface ReconcileFileService {

	/**
	 * 对账文件上传
	 * 
	 * @param params
	 * @return Map<String,Object>
	 */
	Map<String, Object> reconcileFileUpload(Map<String, Object> params);

	/**
	 * 查询银行对账文件
	 * 
	 * @param page
	 * @param importDto
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryReconcileUploadFile(
			Page<ReconcileImportFile> page, WebQueryFile webQueryFile);

	/**
	 * 下载银行对账文件
	 * 
	 * @param params
	 * @return InputStream
	 */
	InputStream downloadReconcileFile(Map<String, Object> params);

	/**
	 * 废除银行对账文件
	 * 
	 * @param params
	 * @return Map<String,Object>
	 */
	Map<String, Object> revokeReconcileFile(Map<String, Object> params);

	/**
	 * 查询银行对账文件明细信息
	 * 
	 * @param params
	 * @param page
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryReconcileFileDetailInfo(
			Page<ReconcileImportRecord> page, Map<String, Object> params);

	/**
	 * 批量对账
	 * 
	 * @param page
	 * @param webQueryFile
	 * @return Map<String,Object>
	 */
	Map<String, Object> batchReconcileInfos(Page<ReconcileImportFile> page,
			WebQueryFile webQueryFile);

	/**
	 * 单笔对账
	 * 
	 * @param params
	 * @return Map<String,Object>
	 */
	Map<String, Object> singleReconcileInfo(Map<String, Object> params);
}
