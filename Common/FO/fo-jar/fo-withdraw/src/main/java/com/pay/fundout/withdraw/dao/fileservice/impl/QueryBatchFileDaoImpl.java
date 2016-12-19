/** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchFileDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-13		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.dao.fileservice.impl;

import static com.pay.fundout.withdraw.common.util.WithDrawConstants.FILE_SERVICE_NAME_SPANCE;

import java.util.List;

import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.model.fileservice.QueryBatchWithDraw;
import com.pay.fundout.withdraw.model.fileservice.WithdrawBatchInfo;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-13
 * @see
 */
@SuppressWarnings("unchecked")
public class QueryBatchFileDaoImpl extends BaseDAOImpl implements
		QueryBatchFileDao {
	@Override
	public Page<WithdrawBatchInfo> findWdBatchInfo4Page(
			Page<WithdrawBatchInfo> page, QueryBatchWithDraw queryBatchWithDraw)
			throws PlatformDaoException {
		return (Page<WithdrawBatchInfo>) super.findByQuery(
				FILE_SERVICE_NAME_SPANCE + "fundout-withdraw-query-inner-file",
				page, queryBatchWithDraw);
	}

	@Override
	public Page<WithdrawBatchInfo> findBatchInfoPage(
			Page<WithdrawBatchInfo> page, QueryBatchWithDraw queryBatchWithDraw)
			throws PlatformDaoException {
		return (Page<WithdrawBatchInfo>) super.findByQuery(
				FILE_SERVICE_NAME_SPANCE + "fundout-withdraw-batch-info", page,
				queryBatchWithDraw);
	}

	@Override
	public Integer updateWdBatchInfo(BatchInfo batchInfo)
			throws PlatformDaoException {
		boolean count = this.update("schedule.fo-UpdateBatchInfo", batchInfo);
		return 2;
	}

	@Override
	public Integer updateWdWorkorder(WithdrawWorkorder withdrawWorkorder)
			throws PlatformDaoException {
		Integer count = this.getSqlMapClientTemplate().update(
				FILE_SERVICE_NAME_SPANCE + "fundout-withdraw-update-workorder",
				withdrawWorkorder);
		return count;
	}

	@Override
	public int updateBatchFileInfo(BatchFileInfo batchFileInfo) {
		boolean count = this.update("schedule.fo-UpdateBatchFileInfo",
				batchFileInfo);
		return 2;
	}

	@Override
	public BatchFileInfo queryBatchFileInfo(BatchFileInfo batchFileInfo) {
		batchFileInfo = (BatchFileInfo) this.findObjectByCriteria(
				"schedule.fo-QueryBatchFileInfo", batchFileInfo);
		return batchFileInfo;
	}

	@Override
	public List<BatchFileInfo> queryBatchFileInfos(BatchFileInfo batchFileInfo) {
		return this
				.findByQuery("schedule.fo-QueryBatchFileInfo", batchFileInfo);
	}

	@Override
	public BatchInfo queryBatchInfo(BatchInfo batchInfo) {
		batchInfo = (BatchInfo) this.findObjectByCriteria(
				"schedule.fo-QueryBatchInfo", batchInfo);
		return batchInfo;
	}

	@Override
	public Page<WithdrawBatchInfo> findWdSubmitBank4Page(
			Page<WithdrawBatchInfo> page, QueryBatchWithDraw queryBatchWithDraw)
			throws PlatformDaoException {
		return (Page<WithdrawBatchInfo>) super.findByQuery(
				FILE_SERVICE_NAME_SPANCE + "fundout-withdraw-query-bank-file",
				page, queryBatchWithDraw);
	}

	public List<BatchRuleConfig> queryBatchRuleConfig() {
		String param = null;
		return super.findByQuery(FILE_SERVICE_NAME_SPANCE
				+ "findBatchRuleConfig", param);
	}

	@Override
	public List<WithdrawWorkorder> queryWorkorders(String batchNum) {
		return super.findByQuery(FILE_SERVICE_NAME_SPANCE
				+ "fundout-withdraw_query_workorder", batchNum);
	}

	@Override
	public Integer updateBatchInfo(BatchInfo batchInfo) {
		super.update("schedule.fo-UpdateBatchInfo", batchInfo);
		return 2;
	}

	@Override
	public List<WithdrawBatchInfo> findBatchInfo(
			QueryBatchWithDraw withdrawBatchInfo)throws PlatformDaoException {
		return (List<WithdrawBatchInfo>)super.findByQuery(
				FILE_SERVICE_NAME_SPANCE + "fundout-withdraw-query-inner-file",withdrawBatchInfo);
	}
}
