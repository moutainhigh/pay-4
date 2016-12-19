package com.pay.fundout.reconcile.service.rcmanualreconciliation.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcAuditDao;
import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcLogDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileFlow;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcLogService;
import com.pay.inf.dao.Page;

/**
 * 手工调账日志Service
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		RcLogServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-7		Volcano.Wu			Create
 */
public class RcLogServiceImpl implements RcLogService{
	
	private RcLogDao rcLogDao;

	private RcAuditDao rcAuditDao;
	
	public void setRcAuditDao(final RcAuditDao rcAuditDao) {
		this.rcAuditDao = rcAuditDao;
	}
	
	public void setRcLogDao(RcLogDao rcLogDao) {
		this.rcLogDao = rcLogDao;
	}

	@Override
	public Page<ReconciliationDTO> queryRcLog(Page<ReconciliationDTO> page,Map<String, Object> params) {
		return rcLogDao.queryRcLog(page, params);
	}

	@Override
	public Map<String,Object> queryRcLogDetail(Map<String, Object> params) {
		
		ReconciliationDTO reconciliationDTO = rcAuditDao.queryReconciliationDTO(params);
		params.put("rc", reconciliationDTO);
		List<ReconcileFlow> reconcileFlows = rcLogDao.queryRcLogDetail(params);
		params.put("reconcileFlows", reconcileFlows);
		return params;
	}

	@Override
	public List<ReconciliationDTO> queryRcLog2Excel(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
}
