/**
 *  File: WithdrawAuditDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.export;

import com.pay.fundout.withdraw.model.flowprocess.export.ExportWithdrawModel;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Jonathen Ni
 * @since 2010-09-14
 * 
 */
public interface ExportWithdrawAuditDao extends BaseDAO<ExportWithdrawModel> {

	// /**
	// * 查询申请提现的数据
	// * @Auther Jonathen Ni
	// */
	// public List<ExportWithdrawModel> findWithdrawOrder(String stmtId,
	// final Page<WithdrawQueryOrder> page, Object... params)
	// throws PlatformDaoException ;

}
