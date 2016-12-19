package com.pay.fundout.reconcile.dao.rcmanualreconciliation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 手工调账审核Dao
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcAuditDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-6 Volcano.Wu Create
 */
public interface RcAuditDao extends BaseDAO {

	/**
	 * 查询手工调账审核记录
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public abstract Page<ReconciliationDTO> queryRcResult(
			Page<ReconciliationDTO> page, Map<String, Object> params);

	/**
	 * 查询手工调账记录的详细信息
	 * 
	 * @param params
	 * @return
	 */
	public abstract ReconciliationDTO queryReconciliationDTO(
			Map<String, Object> params);

	/**
	 * 更新手工调账记录的状态
	 * 
	 * @param params
	 * @return
	 */
	public abstract int updateRcResultStatus(Map<String, Object> params);

	/**
	 * 更新调账申请的状态
	 * 
	 * @param params
	 * @return
	 */
	public abstract int updateRcApplyMainStatus(Map<String, Object> params);

	/**
	 * 批量更新手工调账申请记录的状态
	 * 
	 * @param params
	 * @return
	 */
	public abstract int updateBatchRcApplyMain(List<Map<String, Object>> params);

	/**
	 * 批量更新调账结果的状态
	 * 
	 * @param params
	 * @return
	 */
	public abstract int updateBatchRcResult(List<Map<String, Object>> params);

}
