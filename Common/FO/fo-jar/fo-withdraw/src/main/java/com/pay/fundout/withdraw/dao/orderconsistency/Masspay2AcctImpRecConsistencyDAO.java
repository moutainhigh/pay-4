/**
 *  File: MasspayImportRecordDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010 12-29     jonathen ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.orderconsistency;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayBatchOrder;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * @author jonathen ni
 * 
 */
public interface Masspay2AcctImpRecConsistencyDAO extends BaseDAO {
	/**
	 * 
	 * @param batchPay2AcctBatchNum
	 * @return added by jonathen ni 2010 12-29
	 */
	public List<MasspayImportRecord> queryMassPayImpFileRecByGenOrderFail(
			String stmtId, Map map);

	/**
	 * 根据批次号和付款人会员号获得批量付款带账户的主订单
	 * 
	 * @param map
	 * @return
	 */
	public MasspayBatchOrder getMassPayBatchOrder(String stmtId, Map map);

	/**
	 * 带分页的批量付款到账户的查询
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<MasspayImportRecord> queryMassPayImpFileRecByGenOrderFail(
			String stmtId, Page<MasspayImportRecord> page, Map map);
}
