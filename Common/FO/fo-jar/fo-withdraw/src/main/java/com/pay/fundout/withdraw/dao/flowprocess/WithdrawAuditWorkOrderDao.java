/**
 *  File: WithdrawAuditWorkOrderDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.flowprocess;

import java.util.List;

import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.model.BatchFileRecord;

/**
 * @author Jonathen Ni
 * @since 2010-09-14
 */
public interface WithdrawAuditWorkOrderDao extends BaseDAO {
	/**
	 * 更新工单状态 的DAO
	 * 
	 * @Auther Jonathen Ni
	 */
	public Integer updateWorkOrder(String stmtId,
			List<WithdrawQueryOrder> paramList) throws PlatformDaoException;

	// /**
	// * 更新工单状态 的DAO
	// * @Auther Jonathen Ni
	// */
	// public Integer updateWorkOrder(String stmtId, Object... params)
	// throws PlatformDaoException ;

	/**
	 * 根据工单号查询工单信息
	 * 
	 * @Auther Jonathen Ni
	 */
	public WithdrawWorkorder queryWorkOrderById(String stmtId, Object params)
			throws PlatformDaoException;

	public void saveBatchFileRecord(String string, BatchFileRecord bf);

}
