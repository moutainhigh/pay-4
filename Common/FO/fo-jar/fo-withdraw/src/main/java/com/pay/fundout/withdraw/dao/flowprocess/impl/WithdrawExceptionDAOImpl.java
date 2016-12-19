/**
 *  File: WithdrawExceptionDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess.impl;

import java.util.Map;

import com.pay.fundout.withdraw.dao.flowprocess.WithdrawExceptionDAO;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawExceptionInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author darv
 * 
 */
public class WithdrawExceptionDAOImpl extends
		BaseDAOImpl<WithdrawExceptionInfo> implements WithdrawExceptionDAO {

	@Override
	public Page<WithdrawExceptionInfo> getWithDrawExceptionInfoList(
			Page<WithdrawExceptionInfo> page, Map params) {
		return findByQuery("getWithDrawExceptionInfoList", page, params);
	}
}
