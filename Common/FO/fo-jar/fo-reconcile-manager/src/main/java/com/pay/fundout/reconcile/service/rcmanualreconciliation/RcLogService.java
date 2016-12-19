package com.pay.fundout.reconcile.service.rcmanualreconciliation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.inf.dao.Page;

/**
 * 手工调账日志Service
 * @Description 
 * @project 	fo-reconcile-manager
 * @file 		RcLogService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-7		Volcano.Wu			Create
 */
public interface RcLogService {

	public Page<ReconciliationDTO> queryRcLog(Page<ReconciliationDTO> page,Map<String, Object> params);
	
	public Map<String,Object> queryRcLogDetail(Map<String, Object> params);
	
	//2Excel
	public List<ReconciliationDTO> queryRcLog2Excel(Map<String, Object> params);
}
