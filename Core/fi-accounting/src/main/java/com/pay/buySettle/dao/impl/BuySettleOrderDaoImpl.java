package com.pay.buySettle.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.buySettle.dao.BuySettleOrderDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.model.BuysettleOrder;

public class BuySettleOrderDaoImpl extends BaseDAOImpl<BuysettleOrder> implements BuySettleOrderDAO {

	@Override
	public BuysettleOrder queryByConditions(Map<String, Object> params) {
		return super.findObjectByCriteria("queryConditionsMap", params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsList(Map<String, Object> params) {
		return super.findByCriteria("queryConditionsMap", params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsPage(Map<String, Object> params, Page<BuysettleOrder> page) {
		return super.findByCriteria("queryConditionsMap", params, page);
	}

	@Override
	public BuysettleOrder queryByCondition(BuysettleOrder params) {
		return super.findObjectByCriteria("queryConditions", params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsList(BuysettleOrder params) {
		return super.findByCriteria("queryConditions", params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsPage(BuysettleOrder params, Page<BuysettleOrder> page) {
		return super.findByCriteria("queryConditions", params, page);
	}

	@SuppressWarnings("deprecation")
	@Override
	public long QuerySeq() {
		return (Long) getSqlMapClientTemplate().queryForObject(this.namespace.concat("query_seq"));
	}

}
