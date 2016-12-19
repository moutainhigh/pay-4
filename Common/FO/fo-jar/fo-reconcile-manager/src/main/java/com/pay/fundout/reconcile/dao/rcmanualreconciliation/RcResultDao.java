package com.pay.fundout.reconcile.dao.rcmanualreconciliation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileApplyMain;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 手工调账受理查询
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcResultDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-4 Volcano.Wu Create
 */
public interface RcResultDao extends BaseDAO {

	/**
	 * 手工调账查询分页
	 */
	public Page<ReconciliationDTO> queryRcResult(Page<ReconciliationDTO> page,
			Map<String, Object> params);

	public ReconciliationDTO queryReconciliationDTO(Map<String, Object> params);

	public Long insertRcApplyMain(ReconcileApplyMain reconcileApplyMain);

	public Boolean updateReconcileResult(Map<String, Object> params);

	public ReconcileApplyMain queryApplyMainByPk(Long resultId);

	public List<Long> insertBatchRcApplyMain(List<ReconcileApplyMain> paramsList);

	public Integer updateBatchReconcileResult(
			List<Map<String, Object>> paramsList);

	public List<ReconciliationDTO> queryRcResult(Map<String, Object> params);

	public Integer updateBatchRcApplyMain(List<Map<String, Object>> paramsList);

}
