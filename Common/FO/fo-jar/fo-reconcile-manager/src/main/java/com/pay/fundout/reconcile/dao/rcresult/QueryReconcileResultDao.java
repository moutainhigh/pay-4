/**
 *  File: ReconcileDao.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.dao.rcresult;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.model.rcresult.ReconcileResult;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResultSummary;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 对账DAO
 * 
 * @author henry_zeng
 */
public interface QueryReconcileResultDao extends BaseDAO {

	/**
	 * 查询对账列表
	 * 
	 * @return
	 */
	public Page<ReconcileResultSummary> queryReconcileList(
			Map<String, Object> map, Page<ReconcileResultSummary> pageModel);

	/**
	 * 查询对账详情银行比数列表
	 * 
	 * @param map
	 * @return
	 */
	public Page<ReconcileResult> queryReconcileDetailByBank(
			Map<String, Object> map, Page<ReconcileResult> page);

	/**
	 * 查询对账详情Sys比数列表
	 * 
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<ReconcileResult> queryReconcileDetailBySys(
			Map<String, Object> map, Page<ReconcileResult> page);

	/**
	 * 查询对账列表
	 * 
	 * @return
	 */
	public List<ReconcileResultSummary> queryReconcileSummaryList(
			Map<String, Object> map);

	/**
	 * 查询对账详情银行比数列表
	 * 
	 * @param map
	 * @return
	 */
	public List<ReconcileResult> queryRcResultDetailByBank(
			Map<String, Object> map);

	/**
	 * 查询对账详情Sys比数列表
	 * 
	 * @param map
	 * @param page
	 * @return
	 */
	public List<ReconcileResult> queryRcResultDetailBySys(
			Map<String, Object> map);
}
