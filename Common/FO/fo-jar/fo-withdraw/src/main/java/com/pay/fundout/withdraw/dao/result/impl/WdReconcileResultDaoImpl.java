/** @Description 
 * @project 	poss-withdraw
 * @file 		ImportResultDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.dao.result.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.result.WdReconcileResultDao;
import com.pay.fundout.withdraw.model.result.WithdrawImportFile;
import com.pay.fundout.withdraw.model.result.WithdrawImportRecord;
import com.pay.fundout.withdraw.model.result.WithdrawRcResultSummary;
import com.pay.fundout.withdraw.model.result.WithdrawReconcileResult;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
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
@SuppressWarnings("unchecked")
public class WdReconcileResultDaoImpl extends BaseDAOImpl implements
		WdReconcileResultDao {

	@Override
	public Map<String, Object> callProc(Map<String, Object> param)
			throws PlatformDaoException {
		param = (Map<String, Object>) super.findObjectByCriteria(
				"fo-rc.fundout-withdraw-procwdrrctl", param);
		return param;
	}

	@Override
	public long saveFileImportRecordInfo(
			List<WithdrawImportRecord> withdrawImportRecordList)
			throws PlatformDaoException {
		super.batchCreate("fo-rc.fundout-withdraw-createImportRecord",
				withdrawImportRecordList);
		return 0;
	}

	@Override
	public long saveWithdrawImportFile(WithdrawImportFile withdrawImportFile) {
		long count = (Long) super.create(
				"fo-rc.fundout-withdraw-createImportFile", withdrawImportFile);
		return count;
	}

	@Override
	public int updateImportFileInfo(WithdrawImportFile withdrawImportFile) {
		boolean count = super.update(
				"fo-rc.fundout-withdraw-update-importFile", withdrawImportFile);
		return 2;
	}

	@Override
	public WithdrawRcResultSummary queryWdRcResultSummary(
			Map<String, Object> param) {
		WithdrawRcResultSummary withdrawRcResultSummary = (WithdrawRcResultSummary) super
				.findObjectByCriteria("fo-rc.fundout-withdraw-query-summary",
						param);
		return withdrawRcResultSummary;
	}

	@Override
	public List<WithdrawReconcileResult> queryWdRcResultList(
			Map<String, String> param) {
		List<WithdrawReconcileResult> results = super.findByQuery(
				"fo-rc.fundout-withdraw-query-rcresulttoacc", param);
		return results;
	}

	@Override
	public Page<WithdrawReconcileResult> queryWdRcResult(
			Page<WithdrawReconcileResult> page, Map<String, Object> param) {
		page = super.findByQuery("fo-rc.fundout-withdraw-query-rcresult", page,
				param);
		return page;
	}

	@Override
	public Integer deleteWdImportFile(Map<String, String> params) {
		super.delete("fo-rc.deleteImportedFile", params);
		return 2;
	}

	@Override
	public Integer deleteWdImportFileRecord(Map<String, String> params) {
		super.delete("fo-rc.deleteImportedRecord", params);
		return 2;
	}

	@Override
	public Integer deleteWdImportFileResult(Map<String, String> params) {
		super.delete("fo-rc.deleteImportedResult", params);
		return 2;
	}

	public WithdrawRcResultSummary showBatchDetail(Map<String, String> param) {
		return (WithdrawRcResultSummary) super.findObjectByCriteria(
				"fo-rc.fundout-withdraw-query-batch-detail-summary", param);
	}

}
