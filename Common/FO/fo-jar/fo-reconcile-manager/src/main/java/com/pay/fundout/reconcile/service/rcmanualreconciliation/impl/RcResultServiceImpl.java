package com.pay.fundout.reconcile.service.rcmanualreconciliation.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcAuditDao;
import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcLogDao;
import com.pay.fundout.reconcile.dao.rcmanualreconciliation.RcResultDao;
import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileApplyMain;
import com.pay.fundout.reconcile.model.rcmanualreconciliation.ReconcileFlow;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcResultService;
import com.pay.inf.dao.Page;

/**
 * 手工调账查询
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		RcResultServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-6		Volcano.Wu			Create
 */
public class RcResultServiceImpl implements RcResultService{
	
	private RcResultDao rcResultDao;
	private RcLogDao rcLogDao ;
	private RcAuditDao rcAuditDao;
	
	public void setRcAuditDao(RcAuditDao rcAuditDao) {
		this.rcAuditDao = rcAuditDao;
	}
	
	public void setRcLogDao(final RcLogDao rcLogDao) {
		this.rcLogDao = rcLogDao;
	}
	
	
	public void setRcResultDao(RcResultDao rcResultDao) {
		this.rcResultDao = rcResultDao;
	}

	/**
	 * 手工调账查询分页
	 */
	@Override
	public Page<ReconciliationDTO> queryRcResult(Page<ReconciliationDTO> page,Map<String, Object> params) {
		return rcResultDao.queryRcResult(page,params);
	}

	/**
	 * 调账申请确认详细查询
	 */
	@Override
	public ReconciliationDTO queryReconciliationDTO(Map<String, Object> params) {
		return rcResultDao.queryReconciliationDTO(params);
	}
	
	/**
	 * 新增出款对账调账受理记录
	 */
	
	@Override
	public Boolean insertRcApplyMain(Map<String, Object> params) {
		
		long resultId = Long.valueOf((String) params.get("RESULT_ID"));
		ReconcileApplyMain reconcileApplyMain = rcResultDao.queryApplyMainByPk(resultId);
		long applyId = 0;
		Map<String,Object> updateRcApplyMainMap = new HashMap<String, Object>();
		if(reconcileApplyMain != null && 3 == reconcileApplyMain.getProcessStatus()){
			applyId = reconcileApplyMain.getApplyId();
			updateRcApplyMainMap.putAll(params);
			updateRcApplyMainMap.put("PROCESS_STATUS", 1);
			updateRcApplyMainMap.put("RESULT_ID", resultId);
			rcAuditDao.updateRcApplyMainStatus(updateRcApplyMainMap);
		
		}else{
			reconcileApplyMain = new ReconcileApplyMain();
			reconcileApplyMain.setApplyDate(Calendar.getInstance().getTime());
			reconcileApplyMain.setApplyUser((String)params.get("APPLY_USER"));
			
			reconcileApplyMain.setResultId(Long.valueOf((String) params.get("RESULT_ID")));
			reconcileApplyMain.setTradeSeq((String)params.get("TRADE_SEQ"));
			reconcileApplyMain.setReason((String)params.get("reason"));
			reconcileApplyMain.setProcessStatus(1);
			reconcileApplyMain.setWorkflowId("001");
			applyId = rcResultDao.insertRcApplyMain(reconcileApplyMain);
		}
		ReconcileFlow reconcileFlow = new ReconcileFlow();
		reconcileFlow.setApplyId(applyId);//申请ID
		reconcileFlow.setOpertor((String)params.get("APPLY_USER")); //操作人
		reconcileFlow.setProcessRemarks((String)params.get("reason"));//申请理由 备注
		reconcileFlow.setProcessTime(new Date()); //处理时间
		reconcileFlow.setProcessStatus(1);
		
		rcLogDao.insertRcLog(reconcileFlow);
		
		return updateReconcileResult(params);
	}
	
	/**
	 * 批量申请
	 * @param ids
	 * @param params
	 * @return
	 */
	public void insertBatch(Map<String,Object> params)throws Exception{
		String ids = (String)params.get("ids");
		
		ReconcileApplyMain reconcileApplyMain;
		ReconcileFlow reconcileFlow;
		Map<String,Object> resultMap;
		Map<String,Object> applyMap;
		List<Long> applyIds = new ArrayList<Long>();
		
		if(StringUtils.isNotEmpty(ids)){
			String[] id = ids.split(":");
			List<ReconcileApplyMain> insertReconcileApplyMainList = new ArrayList<ReconcileApplyMain>();
			List<Map<String,Object>> updateReconcileApplyMainList = new ArrayList<Map<String,Object>>();
			List<ReconcileFlow> insertReconcileFlowList = new ArrayList<ReconcileFlow>();
			List<Map<String,Object>> updateReconcileResultList = new ArrayList<Map<String,Object>>();
			
			for (String resultId : id) {
				reconcileApplyMain = rcResultDao.queryApplyMainByPk(Long.valueOf(resultId));
				if(reconcileApplyMain == null){
					reconcileApplyMain = new ReconcileApplyMain();
					reconcileApplyMain.setApplyDate(Calendar.getInstance().getTime());
					reconcileApplyMain.setApplyUser((String)params.get("APPLY_USER"));//申请人
					reconcileApplyMain.setResultId(Long.valueOf(resultId));//对账结果表ID
					reconcileApplyMain.setReason((String)params.get("reason"));
					reconcileApplyMain.setProcessStatus(1);
					reconcileApplyMain.setWorkflowId("001");
					insertReconcileApplyMainList.add(reconcileApplyMain);
				}else{
					resultMap = new HashMap<String, Object>();
					resultMap.put("PROCESS_STATUS", 1);
					resultMap.put("RESULT_ID", resultId);
					
					applyIds.add(reconcileApplyMain.getApplyId());
					updateReconcileApplyMainList.add(resultMap);
				}
				applyMap = new HashMap<String,Object>();
				applyMap.put("RESULT_ID", resultId);
				applyMap.put("REVISE_STATUS", 1);
				updateReconcileResultList.add(applyMap);
			}
			//批量插入申请表
			if(insertReconcileApplyMainList != null && insertReconcileApplyMainList.size() > 0){
				applyIds =  rcResultDao.insertBatchRcApplyMain(insertReconcileApplyMainList);
			}
			
			//批量更新申请表
			if(updateReconcileApplyMainList != null && updateReconcileApplyMainList.size() > 0){
				rcResultDao.updateBatchRcApplyMain(updateReconcileApplyMainList);
			}
			
			for (Long applyId : applyIds) {
				reconcileFlow = new ReconcileFlow();
				reconcileFlow.setApplyId(applyId);//申请ID
				reconcileFlow.setOpertor((String)params.get("APPLY_USER")); //操作人
				reconcileFlow.setProcessRemarks((String)params.get("reason"));//申请理由 备注
				reconcileFlow.setProcessTime(new Date()); //处理时间
				reconcileFlow.setProcessStatus(1);
				insertReconcileFlowList.add(reconcileFlow);
			}
			//批量更新结果表
			rcResultDao.updateBatchReconcileResult(updateReconcileResultList);
			//批量插入日志表
			rcLogDao.insertBatchRcLog(insertReconcileFlowList);
		}
	}
	
	/**
	 * 更新出款对账结果表  0：初始 1调账申请2调账中3调账成功4调账失败
	 */
	@Override
	public Boolean updateReconcileResult(Map<String, Object> params) {
		return rcResultDao.updateReconcileResult(params);
	}

	@Override
	public List<ReconciliationDTO> queryRcResult2Excel(Map<String, Object> params) {
		return rcResultDao.queryRcResult(params);
	}
}
