package com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcAuditDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 手工调账审核Dao
 * 
 * @Description
 * @project fo-reconcile-manager
 * @file RcAuditDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-6 Volcano.Wu Create
 */
public class RcAuditDaoImpl extends BaseDAOImpl implements RcAuditDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aa#queryRcResult
	 * (com.pay.inf.dao.Page, java.util.Map)
	 */
	public Page<ReconciliationDTO> queryRcResult(Page<ReconciliationDTO> page,
			Map<String, Object> params) {
		return (Page<ReconciliationDTO>) findByQuery("RC_MR.queryRcAudit",
				page, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aa#
	 * queryReconciliationDTO(java.util.Map)
	 */
	public ReconciliationDTO queryReconciliationDTO(Map<String, Object> params) {
		return (ReconciliationDTO) super.findObjectByCriteria("RC_MR.queryAuditDetail", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aa#
	 * updateRcResultStatus(java.util.Map)
	 */
	public int updateRcResultStatus(Map<String, Object> params) {
		super.update("RC_MR.updateReconcileResult", params); // 更新出款对账结果表状态
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aaa#
	 * updateRcApplyMainStatus2Pass(java.util.Map)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aa#
	 * updateRcApplyMainStatus(java.util.Map)
	 */
	public int updateRcApplyMainStatus(Map<String, Object> params) {
		super.update("RC_MR.updateReconcileApplyMain", params);// 更新出款对账受理表状态
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aa#
	 * updateBatchRcApplyMain(java.util.List)
	 */
	public int updateBatchRcApplyMain(List<Map<String, Object>> params) {

		return super.updateBatch("RC_MR.updateReconcileApplyMain", params);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.reconcile.dao.rcmanualreconciliation.impl.aa#
	 * updateBatchRcResult(java.util.List)
	 */
	public int updateBatchRcResult(List<Map<String, Object>> params) {
		// TODO Auto-generated method stub
		return super.updateBatch("RC_MR.updateReconcileResult", params);
	}

}
