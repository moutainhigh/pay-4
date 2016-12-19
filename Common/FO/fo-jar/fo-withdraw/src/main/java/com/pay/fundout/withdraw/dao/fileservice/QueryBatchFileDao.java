/** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchFileDao.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-13		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.dao.fileservice;

import java.util.List;

import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.model.fileservice.QueryBatchWithDraw;
import com.pay.fundout.withdraw.model.fileservice.WithdrawBatchInfo;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;

/**
 * <p>
 * 查询提现批次文件DAO
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-13
 * @see
 */
public interface QueryBatchFileDao extends BaseDAO {

	/**
	 * 查询批次文件信息分页操作
	 * 
	 * @param stmtId
	 * @param page
	 * @param params
	 * @return
	 * @throws PlatformDaoException
	 */
	public Page<WithdrawBatchInfo> findWdBatchInfo4Page(
			final Page<WithdrawBatchInfo> page,
			QueryBatchWithDraw queryBatchWithDraw) throws PlatformDaoException;

	/**
	 * 查询批次信息分页操作
	 * 
	 * @param page
	 * @param queryBatchWithDraw
	 * @return
	 * @throws PlatformDaoException
	 */
	public Page<WithdrawBatchInfo> findBatchInfoPage(
			Page<WithdrawBatchInfo> page, QueryBatchWithDraw queryBatchWithDraw)
			throws PlatformDaoException;

	/**
	 * 查询提交文件分页操作
	 * 
	 * @return
	 */
	public Page<WithdrawBatchInfo> findWdSubmitBank4Page(
			final Page<WithdrawBatchInfo> page,
			QueryBatchWithDraw queryBatchWithDraw) throws PlatformDaoException;

	/**
	 * 更新工单表
	 * 
	 * @param stmtId
	 * @param id
	 * @return
	 * @throws PlatformDaoException
	 */
	public Integer updateWdWorkorder(WithdrawWorkorder withdrawWorkorder)
			throws PlatformDaoException;

	/**
	 * 更新批次信息表
	 * 
	 * @param stmtId
	 * @param id
	 * @return
	 * @throws PlatformDaoException
	 */
	public Integer updateWdBatchInfo(BatchInfo batchInfo)
			throws PlatformDaoException;

	/**
	 * 更新批次文件信息
	 * 
	 * @param batchFileInfo
	 * @return
	 */
	public int updateBatchFileInfo(BatchFileInfo batchFileInfo);

	/**
	 * 根据batchFileInfo去查询批次文件信息
	 * 
	 * @param batchFileInfo
	 * @return
	 */
	public BatchFileInfo queryBatchFileInfo(BatchFileInfo batchFileInfo);

	/**
	 * 根据batchFileInfo去查询批次文件信息()
	 * 
	 * @param batchFileInfo
	 * @return
	 */
	public List<BatchFileInfo> queryBatchFileInfos(BatchFileInfo batchFileInfo);

	/**
	 * 根据batchFileInfo去查询批次信息
	 * 
	 * @param batchInfo
	 * @return
	 */
	public BatchInfo queryBatchInfo(BatchInfo batchInfo);

	/**
	 * 获取批次信息表
	 * 
	 * @return
	 */
	public List<BatchRuleConfig> queryBatchRuleConfig();

	/**
	 * 获取工单所有信息
	 * 
	 * @param batchNum
	 * @return
	 */
	public List<WithdrawWorkorder> queryWorkorders(String batchNum);

	/**
	 * 更新批次信息
	 * 
	 * @param batchInfo
	 * @return java.lang.Integer 更新记录数
	 */
	public Integer updateBatchInfo(BatchInfo batchInfo);

	public List<WithdrawBatchInfo> findBatchInfo(
			QueryBatchWithDraw withdrawBatchInfo);

}
