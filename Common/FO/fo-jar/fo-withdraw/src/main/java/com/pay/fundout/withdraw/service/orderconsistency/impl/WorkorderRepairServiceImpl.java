/**
 *  File: WorkorderRepairServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-30      Sunsea.Li      Changes
 *  
 */
package com.pay.fundout.withdraw.service.orderconsistency.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.fundout.withdraw.service.orderconsistency.WorkorderRepairService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * 后台支撑功能-工单状态及工作流修复服务相关实现
 * 
 * @author Sunsea.Li
 * 
 */
public class WorkorderRepairServiceImpl implements WorkorderRepairService {

	private BaseDAO baseDao; // 处理数据库操作的基础DAO
	private WithdrawOrderAuditService wdOrdAuditService;

	public void setBaseDao(BaseDAO<Object> baseDao) {
		this.baseDao = baseDao;
	}

	public void setWdOrdAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.orderconsistency.WorkorderRepairService
	 * #queryWorkorders(com.pay.inf.dao.Page, com.pay.fundout.withdraw
	 * .dto.orderconsistency.workorderRepair.WorkorderRepairDTO)
	 */
	@Override
	public Map<String, Object> queryWorkorders(final Map paraMap,
			Page<FundoutOrder> resultPage) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultPage = (Page<FundoutOrder>) baseDao
				.findByQuery("workorderrepair.getWorkordersByCondition",
						resultPage, paraMap);
		resultMap.put("page", resultPage);
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.orderconsistency.WorkorderRepairService
	 * #workorderRepairRdTx(com.pay.fundout.withdraw.dto.orderconsistency.
	 * workorderRepair.WorkorderRepairDTO)
	 */
	@Override
	public Map<String, Object> workorderRepairRdTx(final String orderId)
			throws PossException {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		wdOrdAuditService.startWorkFlowRdTx(orderId);
		resultMap.put("err", "修复成功！");
		return resultMap;
	}

}
