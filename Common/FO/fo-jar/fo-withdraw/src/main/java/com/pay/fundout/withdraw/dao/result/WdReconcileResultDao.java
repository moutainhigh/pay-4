/** @Description 
 * @project 	poss-withdraw
 * @file 		ImportResultDao.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.dao.result;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.model.result.WithdrawImportFile;
import com.pay.fundout.withdraw.model.result.WithdrawImportRecord;
import com.pay.fundout.withdraw.model.result.WithdrawRcResultSummary;
import com.pay.fundout.withdraw.model.result.WithdrawReconcileResult;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * <p>
 * 导入结果DAO处理
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see
 */
public interface WdReconcileResultDao extends BaseDAO {

	/**
	 * 【withdrawImportFile:导入文件信息对象】 保存导入文件信息表
	 * 
	 * @param withdrawImportFile
	 * @return
	 */
	public long saveWithdrawImportFile(WithdrawImportFile withdrawImportFile);

	/**
	 * 【withdrawImportRecordList:导入文件明细列表】 批量保存文件明细
	 * 
	 * @param pojo
	 * @return
	 */
	public long saveFileImportRecordInfo(
			List<WithdrawImportRecord> withdrawImportRecordList)
			throws PlatformDaoException;

	/**
	 * 【withdrawImportFile:更新文件导入信息表】
	 * 
	 * @param importFile
	 * @return
	 */
	public int updateImportFileInfo(WithdrawImportFile withdrawImportFile);

	/**
	 * 【in_batch_num :批次号 in_bank_code :银行编号 in_file_ky :文件编号 out_rss_val :返回结果
	 * 】 调用存储过程
	 * 
	 * @param param
	 * @return
	 * @throws PlatformDaoException
	 */
	public Map<String, Object> callProc(Map<String, Object> param)
			throws PlatformDaoException;

	/**
	 * 【bankCode：银行编号 batchNum：批次号 】查询一批次返回银行结果的汇总信息
	 * 
	 * @param bankCode
	 * @param batchNum
	 * @return
	 */
	public WithdrawRcResultSummary queryWdRcResultSummary(
			Map<String, Object> param);

	/**
	 * 【 bankCode：银行编号 batchNum：批次号 busiFlag: 对账状态 】
	 * 
	 * @param param
	 * @return
	 */
	public Page<WithdrawReconcileResult> queryWdRcResult(
			Page<WithdrawReconcileResult> page, Map<String, Object> param);

	/**
	 * 【 bankCode：银行编号 batchNum：批次号 】
	 * 
	 * @param params
	 * @return
	 */
	public Integer deleteWdImportFile(Map<String, String> params);

	/**
	 * 【 bankCode：银行编号 batchNum：批次号 】
	 * 
	 * @param params
	 * @return
	 */
	public Integer deleteWdImportFileRecord(Map<String, String> params);

	/**
	 * 【 bankCode：银行编号 batchNum：批次号 】
	 * 
	 * @param params
	 * @return
	 */
	public Integer deleteWdImportFileResult(Map<String, String> params);

	/**
	 * 【 bankCode：银行编号 batchNum：批次号 】
	 * 
	 * @param param
	 * @return
	 */
	public List<WithdrawReconcileResult> queryWdRcResultList(
			Map<String, String> param);

	/**
	 * 查询导入批次详细信息
	 * 
	 * @param param
	 * @auther Jonathen Ni
	 */
	public WithdrawRcResultSummary showBatchDetail(Map<String, String> param);
}
