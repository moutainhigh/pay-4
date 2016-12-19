package com.pay.fundout.reconcile.service.rcmanualreconciliation.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcAuditDao;
import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcLogDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileFlow;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcAuditService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * 手工调账审核
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		RcAuditServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-6		Volcano.Wu			Create
 */
public class RcAuditServiceImpl implements RcAuditService{
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	private RcAuditDao rcAuditDao;
	
	public void setRcAuditDao(RcAuditDao rcAuditDao) {
		this.rcAuditDao = rcAuditDao;
	}
	
	private RcLogDao rcLogDao;
	
	public void setRcLogDao(final RcLogDao rcLogDao) {
		this.rcLogDao = rcLogDao;
	}
	
	@Override
	public Page<ReconciliationDTO> queryRcResult(Page<ReconciliationDTO> page,Map<String, Object> params) {
		return rcAuditDao.queryRcResult(page,params);
	}
	
	@Override
	public ReconciliationDTO queryReconciliationDTO(Map<String, Object> params) {
		return rcAuditDao.queryReconciliationDTO(params);
	}
	
	@Override
	public Boolean processPassRdTx(Map<String, Object> params) throws PossException {
		
		ReconcileFlow reconcileFlow = new ReconcileFlow();
		reconcileFlow.setApplyId(Long.valueOf((String)params.get("APPLY_ID")));//申请ID
		reconcileFlow.setOpertor((String)params.get("APPLY_USER")); //操作人
		reconcileFlow.setProcessRemarks((String)params.get("reason"));//申请理由 备注
		reconcileFlow.setProcessStatus((Integer)(params.get("LOG_PROCESS_STATUS")));//出款对账调账日志表－处理状态
		reconcileFlow.setProcessTime(new Date()); //处理时间
		try{
			//1 insert 日志表为审核通过
			
			rcLogDao.insertRcLog(reconcileFlow);
			
			//2 update 结果表状态为审核通过
			
			rcAuditDao.updateRcResultStatus(params);
			
			//3 update 申请表状态为审核通过
			
			rcAuditDao.updateRcApplyMainStatus(params);
			
			return Boolean.TRUE ;
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new PossException(e.getMessage(),ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		
		
	}
	
	@Override
	public Boolean processRejectRdTx(Map<String, Object> params) throws PossException {
		ReconcileFlow reconcileFlow = new ReconcileFlow();
		reconcileFlow.setApplyId(Long.valueOf((String)params.get("APPLY_ID")));//申请ID
		reconcileFlow.setOpertor((String)params.get("APPLY_USER")); //操作人
		reconcileFlow.setProcessRemarks((String)params.get("reason"));//申请理由 备注
		reconcileFlow.setProcessStatus((Integer)(params.get("LOG_PROCESS_STATUS")));//出款对账调账日志表－处理状态
		reconcileFlow.setProcessTime(new Date()); //处理时间
		try{
			//1 insert 日志表为审核通过
			
			rcLogDao.insertRcLog(reconcileFlow);
			
			//2 update 结果表状态为审核通过
			
			rcAuditDao.updateRcResultStatus(params);
			
			//3 update 申请表状态为审核通过
			
			rcAuditDao.updateRcApplyMainStatus(params);
			
			return Boolean.TRUE ;
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new PossException(e.getMessage(),ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}
	
	@Override
	public void processBatchPassRdTx(String ids, Map<String, Object> params)
			throws PossException {
		try {
			ReconcileFlow reconcileFlow;
			if (StringUtils.isNotEmpty(ids)) {
				List<Map<String, Object>> updateLists = new ArrayList<Map<String, Object>>();
				List<ReconcileFlow> insertLists = new ArrayList<ReconcileFlow>();
				String[] id = ids.split(":");
				for (String idStr : id) {
					if (StringUtils.isNotEmpty(idStr)) {
						String[] idInfo = idStr.split(",");
						reconcileFlow = new ReconcileFlow();
						reconcileFlow.setApplyId(Long.valueOf(idInfo[1]));// 申请ID
						reconcileFlow.setOpertor((String)params.get("APPLY_USER")); // 操作人
						reconcileFlow.setProcessRemarks((String)params.get("reason"));// 申请理由 备注
						reconcileFlow.setProcessStatus((Integer)params.get("LOG_PROCESS_STATUS"));// 出款对账调账日志表－处理状态
						reconcileFlow.setProcessTime(Calendar.getInstance().getTime()); // 处理时间
						
						Map<String,Object> newMap = new HashMap<String,Object>();
						newMap.putAll(params);
						newMap.put("RESULT_ID", idInfo[0]);
						updateLists.add(newMap);
						insertLists.add(reconcileFlow);

					}
				}
				rcAuditDao.updateBatchRcResult(updateLists);
				rcAuditDao.updateBatchRcApplyMain(updateLists);
				rcLogDao.insertBatchRcLog(insertLists);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new PossException(e.getMessage(),
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}
	
	@Override
	public void processBatchRejectRdTx(String ids,Map<String, Object> params) throws PossException{
		ReconcileFlow reconcileFlow;
		try {
			if (StringUtils.isNotEmpty(ids)) {
				String[] id = ids.split(":");
				List<Map<String, Object>> updateLists = new ArrayList<Map<String, Object>>();
				List<ReconcileFlow> insertLists = new ArrayList<ReconcileFlow>();
				Map<String,Object> newMap;
				for (String idStr : id) {
					if (StringUtils.isNotEmpty(idStr)) {
						String[] idInfo = idStr.split(",");
						reconcileFlow = new ReconcileFlow();
						reconcileFlow.setApplyId(Long.valueOf(idInfo[1]));// 申请ID
						reconcileFlow.setOpertor((String)params.get("APPLY_USER")); // 操作人
						reconcileFlow.setProcessRemarks((String)params.get("reason"));// 申请理由 备注
						reconcileFlow.setProcessStatus((Integer)params.get("LOG_PROCESS_STATUS"));// 出款对账调账日志表－处理状态
						reconcileFlow.setProcessTime(new Date()); // 处理时间
						newMap = new HashMap<String,Object>();
						newMap.putAll(params);
						newMap.put("RESULT_ID", idInfo[0]);
						updateLists.add(newMap);
						insertLists.add(reconcileFlow);
					}
				}

				rcAuditDao.updateBatchRcResult(updateLists);
				rcAuditDao.updateBatchRcApplyMain(updateLists);
				rcLogDao.insertBatchRcLog(insertLists);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	@Override
	public List<ReconciliationDTO> queryRcResult2Excel(
			Map<String, Object> params) {
		
		return null;
	}
}
