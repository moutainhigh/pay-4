package com.pay.acct.buySettleParities.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.acct.buySettleParities.dao.AccApplyCheckDao;
import com.pay.acct.buySettleParities.model.AccApplyCheck;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings({ "deprecation", "unchecked" })
public class AccApplyCheckDaoImpl extends BaseDAOImpl<AccApplyCheck> implements AccApplyCheckDao{
	
	@Override
	public List<AccApplyCheck> queryConditions(AccApplyCheck accApplyCheck) {
		return (List<AccApplyCheck>) this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("queryConditions"),accApplyCheck);
	}

	@Override
	public List<AccApplyCheck> queryByCriteria(Map<String, Object> params) {
		return (List<AccApplyCheck>) this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("queryConditionsMap"),params);
	}

	@Override
	public List<AccApplyCheck> queryByCriteria(Map<String, Object> params,
			Page<Object> page) {
		return this.findByCriteria("queryConditionsMap", params, page);
	}

	@Override
	public Integer queryByCriteriaCount(Map<String, Object> params) {
		 Object object = this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("queryConditionsMap_COUNT"),params);
		return (Integer)object;
	}
}
