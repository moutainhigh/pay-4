/**
 *  File: WorkorderRepairService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-30      Sunsea.Li      Changes
 *  
 */
package com.pay.fundout.withdraw.service.orderconsistency;

import java.util.Map;

import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * 后台支撑功能-工单状态及工作流修复服务接口
 * 
 * @author Sunsea.Li
 * 
 */
public interface WorkorderRepairService {
	/**
	 * 查询工单列表
	 * 
	 * @param resultPage
	 * @param workorderRepairDTO
	 * @return
	 */
	public Map<String, Object> queryWorkorders(final Map paraMap,
			Page<FundoutOrder> resultPage);

	/**
	 * 工单状态及工作流修复(处理动作)
	 * 
	 * @param workorderRepairDTO
	 * @return
	 * @throws PossException
	 */
	public Map<String, Object> workorderRepairRdTx(final String orderId)
			throws PossException;
}
