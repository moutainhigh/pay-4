/**
 *  File: WithdrawExceptionDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess;

import java.util.Map;

import com.pay.fundout.withdraw.model.flowprocess.WithdrawExceptionInfo;
import com.pay.inf.dao.Page;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface WithdrawExceptionDAO {
	/**
	 * 得到异常订单的信息
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<WithdrawExceptionInfo> getWithDrawExceptionInfoList(
			Page<WithdrawExceptionInfo> page, Map params);
}
