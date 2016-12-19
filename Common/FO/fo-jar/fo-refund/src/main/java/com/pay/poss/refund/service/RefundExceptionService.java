/**
 *  File: RefundExceptionService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-7     darv      Changes
 *  
 *
 */
package com.pay.poss.refund.service;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.refund.model.RefundExceptionInfo;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface RefundExceptionService {
	/**
	 * 得到充退异常数据列表
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<RefundExceptionInfo> getRefundExceptionInfoList(Page<RefundExceptionInfo> page, Map params);

	/**
	 *　为工单分配执行人
	 * 
	 * @param orderKy
	 * @throws PossException
	 */
	//public void assignOwnerRdTx(String workflowKy) throws PossException;

	/**
	 * 为工单关联工作流水
	 * 
	 * @param orderKy
	 * @throws PossException
	 */
	public void updateWorkOrderRdTx(String orderKy) throws PossException;
}
