package com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcResultDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileApplyMain;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 手工调账受理查询
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcResultDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-4 Volcano.Wu Create
 */
public class RcResultDaoImpl extends BaseDAOImpl implements RcResultDao {

	/**
	 * 手工调账查询分页
	 */
	@Override
	public Page<ReconciliationDTO> queryRcResult(Page<ReconciliationDTO> page,
			Map<String, Object> params) {
		return (Page<ReconciliationDTO>) findByQuery("RC_MR.queryRcResult",
				page, params);
	}

	@Override
	public List<ReconciliationDTO> queryRcResult(Map<String, Object> params) {
		return findByQuery("RC_MR.queryRcResult", params);
	}

	/**
	 * 调账申请确认详细查询
	 */
	@Override
	public ReconciliationDTO queryReconciliationDTO(Map<String, Object> params) {
		return (ReconciliationDTO) findObjectByCriteria("RC_MR.queryApplyDetail",
				params);
	}

	/**
	 * 新增出款对账调账受理记录
	 */
	@Override
	public Long insertRcApplyMain(ReconcileApplyMain reconcileApplyMain) {
		return (Long) create("RC_MR.insertRcApplyMain", reconcileApplyMain);
	}

	/**
	 * 更新出款对账结果表 0：初始 1调账申请2调账中3调账成功4调账失败
	 */
	@Override
	public Boolean updateReconcileResult(Map<String, Object> params) {
		return update("RC_MR.updateReconcileResult", params);
	}

	/**
	 * 批量更新对账结果表
	 * 
	 * @param paramsList
	 * @return
	 */
	@Override
	public Integer updateBatchReconcileResult(
			List<Map<String, Object>> paramsList) {
		return updateBatch("RC_MR.updateReconcileResult", paramsList);
	}

	/**
	 * 查询申请主表是否已经存在
	 * 
	 * @param resultId
	 * @return
	 */
	public ReconcileApplyMain queryApplyMainByPk(Long resultId) {
		return (ReconcileApplyMain) super.findObjectByCriteria(
				"RC_MR.queryApplyMainByPk", resultId);
	}

	/**
	 * 批量新增
	 */
	@Override
	public List<Long> insertBatchRcApplyMain(List<ReconcileApplyMain> paramsList) {
		return super.batchCreate("RC_MR.insertRcApplyMain", paramsList);
	}

	/**
	 * 批量更新出款对账受理表
	 * 
	 * @param paramsList
	 * @return Integer
	 */
	@Override
	public Integer updateBatchRcApplyMain(List<Map<String, Object>> paramsList) {
		return updateBatch("RC_MR.updateReconcileApplyMain", paramsList);
	}

}
