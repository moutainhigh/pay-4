/**
 *  File: RefundExceptionDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-7     darv      Changes
 *  
 *
 */
package com.pay.poss.refund.dao.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.refund.dao.RefundExceptionDao;
import com.pay.poss.refund.model.RefundExceptionInfo;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class RefundExceptionDaoImpl extends BaseDAOImpl<RefundExceptionInfo>
		implements RefundExceptionDao {
	@Override
	public Page<RefundExceptionInfo> getRefundExceptionInfoList(
			Page<RefundExceptionInfo> page, Map params) {
		return findByQuery("getRefundExceptionInfoList", page, params);
	}

}
