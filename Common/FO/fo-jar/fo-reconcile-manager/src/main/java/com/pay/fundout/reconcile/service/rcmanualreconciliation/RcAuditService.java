package com.pay.fundout.reconcile.service.rcmanualreconciliation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcAuditDao;
import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcLogDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * 手工调账审核接口
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		RcAuditService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-6		Volcano.Wu			Create
 */
public interface RcAuditService {

	public abstract void setRcAuditDao(RcAuditDao rcAuditDao);

	public abstract void setRcLogDao(final RcLogDao rcLogDao);

	/**
	 * 手工调账审核分页
	 */
	public abstract Page<ReconciliationDTO> queryRcResult(
			Page<ReconciliationDTO> page, Map<String, Object> params);

	/**
	 * 审核确认信息查询
	 */
	public abstract ReconciliationDTO queryReconciliationDTO(
			Map<String, Object> params);

	/**
	 * 审核通过
	 */
	public abstract Boolean processPassRdTx(Map<String, Object> params)
			throws PossException;

	/**
	 * 审核驳回
	 */
	public abstract Boolean processRejectRdTx(Map<String, Object> params)throws PossException;

	/**
	 * 批量审核通过
	 */
	public abstract void processBatchPassRdTx(String ids,
			Map<String, Object> params)throws PossException;

	/**
	 * 批量审核驳回
	 */
	public abstract void processBatchRejectRdTx(String ids,
			Map<String, Object> params)throws PossException;

	public abstract List<ReconciliationDTO> queryRcResult2Excel(
			Map<String, Object> params);

}
