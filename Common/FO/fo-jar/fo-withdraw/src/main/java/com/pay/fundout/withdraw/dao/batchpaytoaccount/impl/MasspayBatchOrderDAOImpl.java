/**
 *  File: MasspayBatchOrderDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.batchpaytoaccount.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayBatchOrderDAO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayBatchOrder;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author darv
 * 
 */
public class MasspayBatchOrderDAOImpl extends BaseDAOImpl<MasspayBatchOrder>
		implements MasspayBatchOrderDAO {

	@Override
	public long createMasspayBatchOrder(MasspayBatchOrder masspayBatchOrder) {
		return (Long) this.create("create", masspayBatchOrder);
	}

	@Override
	public Long getSeqId() {
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("getSeqId"));
	}

	@Override
	public boolean updateBatchOrderStatus(MasspayBatchOrder masspayBatchOrder) {
		return this.update("update", masspayBatchOrder);
	}

	@Override
	public Long getDayTotalAmount(Long memberCode) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getDayTotalAmount"), memberCode);
	}

	@Override
	public Long getMonthTotalAmount(Long memberCode) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMonthTotalAmount"), memberCode);
	}

	@Override
	public Integer getMonthTotalCount(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMonthTotalCount"), memberCode);
	}

	@Override
	public Integer getDayTotalCount(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getDayTotalCount"), memberCode);
	}

	@Override
	public List<MasspayBatchOrder> getCompleteMassPaytoAcctOrder() {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("getCompleteMassPaytoAcctOrder"));
	}
}
