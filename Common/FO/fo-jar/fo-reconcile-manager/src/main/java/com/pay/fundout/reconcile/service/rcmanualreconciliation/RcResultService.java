package com.pay.fundout.reconcile.service.rcmanualreconciliation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.inf.dao.Page;

/**
 * 手工调账 接口
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		RcResultService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-4		Volcano.Wu			Create
 */
public interface RcResultService {
	
	/**
	 * 手工调账查询分页
	 */
	public Page<ReconciliationDTO> queryRcResult(Page<ReconciliationDTO> page,Map<String, Object> params);
	
	/**
	 * 调账申请确认
	 */
	public ReconciliationDTO queryReconciliationDTO(Map<String, Object> params);
	
	/**
	 * 出款对账调账受理表
	 */
	public Boolean insertRcApplyMain(Map<String, Object> params);
	
	/**
	 * 更新出款对账结果表
	 */
	public Boolean updateReconcileResult(Map<String, Object> params);
	
	/**
	 * 批量申请
	 * @param ids
	 * @param params
	 */
	public void insertBatch(Map<String,Object> params)throws Exception;
	
	//2Excel
	public List<ReconciliationDTO> queryRcResult2Excel(Map<String, Object> params);

}
