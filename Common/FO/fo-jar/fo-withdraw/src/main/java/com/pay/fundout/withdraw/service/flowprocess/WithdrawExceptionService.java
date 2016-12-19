/**
 *  File: WithdrawExceptionService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess;

import java.util.Map;

import com.pay.fundout.withdraw.model.flowprocess.WithdrawExceptionInfo;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * @author darv
 * 
 */
public interface WithdrawExceptionService {
	/**
	 * 得到异常订单的信息
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<WithdrawExceptionInfo> getWithDrawExceptionInfoList(Page<WithdrawExceptionInfo> page, Map params);

	/**
	 * 为生成了工单但没有工作流的更新工作流id
	 * 
	 * @param workorder
	 */
	public void updateWorkOrderOfExceptionOrderRdTx(String orderId) throws PossException;
}
