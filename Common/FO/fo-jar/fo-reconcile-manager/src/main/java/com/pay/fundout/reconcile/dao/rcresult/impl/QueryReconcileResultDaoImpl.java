/**
 *  File: ReconcileDaoImpl.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.dao.rcresult.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dao.rcresult.QueryReconcileResultDao;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResult;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResultSummary;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 对账DAO实现
 * 
 * @author henry_zeng
 */
@SuppressWarnings("unchecked")
public class QueryReconcileResultDaoImpl extends BaseDAOImpl implements
		QueryReconcileResultDao {

	public Page<ReconcileResultSummary> queryReconcileList(
			Map<String, Object> map, Page<ReconcileResultSummary> page) {
		return super.findByQuery("queryReconcileResultList", page, map);
	}

	public Page<ReconcileResult> queryReconcileDetailByBank(
			Map<String, Object> map, Page<ReconcileResult> page) {
		return (Page<ReconcileResult>) super.findByQuery(
				"queryReconcileDetailByBank", page, map);
	}

	public Page<ReconcileResult> queryReconcileDetailBySys(
			Map<String, Object> map, Page<ReconcileResult> page) {
		return (Page<ReconcileResult>) super.findByQuery(
				"queryReconcileDetailBySys", page, map);
	}

	@Override
	public List<ReconcileResult> queryRcResultDetailByBank(
			Map<String, Object> map) {

		return super.findByQuery("queryReconcileDetailByBank", map);
	}

	@Override
	public List<ReconcileResult> queryRcResultDetailBySys(
			Map<String, Object> map) {
		return super.findByQuery("queryReconcileDetailBySys", map);
	}

	@Override
	public List<ReconcileResultSummary> queryReconcileSummaryList(
			Map<String, Object> map) {
		return super.findByQuery("queryReconcileResultList2Excel", map);
	}

}
