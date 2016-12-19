package com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcLogDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileFlow;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * 手工调账日志Dao
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcLogDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-7 Volcano.Wu Create
 */
public class RcLogDaoImpl extends BaseDAOImpl implements RcLogDao {

	/**
	 * 手工调账日志查询分页
	 */
	@Override
	public Page<ReconciliationDTO> queryRcLog(Page<ReconciliationDTO> page,
			Map<String, Object> params) {
		return (Page<ReconciliationDTO>) findByQuery("RC_MR.queryRcLog", page,
				params);
	}

	/**
	 * 手工调账日志详细
	 */
	@Override
	public List<ReconcileFlow> queryRcLogDetail(Map<String, Object> params) {
		return findByQuery("RC_MR.queryRcLogDetail", params);
	}

	/**
	 * 新增日志
	 * 
	 * @param reconcileFlow
	 * @return
	 */
	public long insertRcLog(ReconcileFlow reconcileFlow) {
		return (Long) super.create("RC_MR.insertReconcileFlow", reconcileFlow);// 新增对账调账日志
	}

	@Override
	public List<Long> insertBatchRcLog(List<ReconcileFlow> reconcileFlows)
			throws PlatformDaoException {
		return super.batchCreate("RC_MR.insertReconcileFlow", reconcileFlows);
	}

}
