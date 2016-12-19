package com.pay.fundout.reconcile.dao.rcmanualreconciliation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileFlow;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * 手工调账日志接口
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcLogDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-7 Volcano.Wu Create
 */
public interface RcLogDao extends BaseDAO {

	/**
	 * 手工调账日志分页查询
	 * 
	 * @param page
	 * @param params
	 * @return Page
	 */
	public Page<ReconciliationDTO> queryRcLog(Page<ReconciliationDTO> page,
			Map<String, Object> params);

	/**
	 * 手工调账日志详细
	 * 
	 * @param params
	 * @return ReconciliationDTO
	 */
	public List<ReconcileFlow> queryRcLogDetail(Map<String, Object> params);

	/**
	 * 新增日志
	 * 
	 * @param reconcileFlow
	 * @return
	 */
	public long insertRcLog(ReconcileFlow reconcileFlow);

	/**
	 * 批量插入日志表
	 * 
	 * @param reconcileFlows
	 * @return
	 * @throws PlatformDaoException
	 */
	public List<Long> insertBatchRcLog(List<ReconcileFlow> reconcileFlows)
			throws PlatformDaoException;

}
