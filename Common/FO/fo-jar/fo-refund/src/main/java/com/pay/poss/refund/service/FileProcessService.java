/**
 *  File: FileProcessService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-13      Sunsea_Li      Changes
 *  
 *
 */
package com.pay.poss.refund.service;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.refund.model.BatchFileInfo;
import com.pay.poss.refund.model.RefundReconcileResult;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.model.WebRefundUploadDTO;

/**
 * 充退批次文件相关操作服务类
 * 
 * @author Sunsea_Li
 * 
 */
public interface FileProcessService {
	/**
	 * 处理文件上传
	 * 
	 * @param webRefundUploadDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> processUploadRdTx(
			WebRefundUploadDTO webRefundUploadDTO) throws PossException;

	/**
	 * 查询对账结果统计数据
	 * 
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> queryResultStatistics(
			WebQueryRefundDTO webQueryRefundDTO) throws PossException;

	/**
	 * 查询对账结果信息列表
	 * 
	 * @param resultPage
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> queryResultList(
			Page<RefundReconcileResult> resultPage,
			WebQueryRefundDTO webQueryRefundDTO) throws PossException;

	/**
	 * 完全匹配交易进行中 的 成功失败记账处理
	 * 
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> processSucess(WebQueryRefundDTO webQueryRefundDTO)
			throws PossException;

	/**
	 * 查询 银行状态文件导入 列表
	 * 
	 * @param resultPage
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> queryRefundImportFile(
			Page<BatchFileInfo> resultPage, WebQueryRefundDTO webQueryRefundDTO)
			throws PossException;

	/**
	 * 查询 文件信息 列表(内/外部文件)
	 * 
	 * @param resultPage
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> queryBatchFileInfo(
			Page<BatchFileInfo> resultPage, WebQueryRefundDTO webQueryRefundDTO)
			throws PossException;

	/**
	 * 修改 文件信息表 包括文件下载次数，状态，更新时间等
	 * 
	 * @param batchFileInfo
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> updateBatchFileInfo(BatchFileInfo batchFileInfo)
			throws PossException;

	/**
	 * 废除已经导入的文件（相当于退回导入）
	 * 
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> dropImportedFile(
			WebQueryRefundDTO webQueryRefundDTO) throws PossException;

	/**
	 * 确认导入动作 （多个文件一起）
	 * 
	 * @param webQueryRefundDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> confirmImportedBatch(
			WebQueryRefundDTO webQueryRefundDTO) throws PossException;

	/**
	 * 废除批次信息
	 * 
	 * @param batchNum
	 * @throws PossException
	 */
	public void dropBatchInfoRdTx(Map<String, Object> outMap)
			throws PossException;

	/**
	 * 重成批次信息
	 * 
	 * @param batchNum
	 * @throws PossException
	 */
	public void reBuildBatchInfo(Map<String, Object> outMap)
			throws PossException;
}
