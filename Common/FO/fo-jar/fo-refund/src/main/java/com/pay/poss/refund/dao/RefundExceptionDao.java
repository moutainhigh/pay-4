/**
 *  File: RefundExceptionDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-7     darv      Changes
 *  
 *
 */
package com.pay.poss.refund.dao;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.refund.model.RefundExceptionInfo;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface RefundExceptionDao {
	/**
	 * 得到充退异常数据列表
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<RefundExceptionInfo> getRefundExceptionInfoList(
			Page<RefundExceptionInfo> page, Map params);

}
