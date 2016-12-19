/**
 *  File: ReconcileService.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.service.rcresult;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultDTO;
import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultSummaryDTO;
import com.pay.inf.dao.Page;

/**
 * 对账Serivce接口
 * 
 * @author Sandy_Yang
 */
public interface QueryReconcileResultService {
	
	/**
	 * 查询对账列表
	 * 
	 * @return
	 */
	public Page<ReconcileResultSummaryDTO> queryReconcileList(Map<String,Object> map,Page<ReconcileResultSummaryDTO> page);

	/**
	 * 查询对账详情列表
	 * 
	 * @param map
	 * @return
	 */
	public Page<ReconcileResultDTO> queryReconcileDetail(Map<String, Object> map , Page<ReconcileResultDTO> page);
	
	/**
	 * 查询对账列表
	 * 
	 * @return
	 */
	public List<ReconcileResultSummaryDTO> queryReconcileList2Excel(Map<String,Object> map);
	
	/**
	 * 查询对账详情列表
	 * 
	 * @param map
	 * @return
	 */
	public List<ReconcileResultDTO> queryReconcileDetail2Excel(Map<String, Object> map);
}
