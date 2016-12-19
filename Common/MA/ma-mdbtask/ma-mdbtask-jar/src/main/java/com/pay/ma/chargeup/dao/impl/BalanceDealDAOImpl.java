package com.pay.ma.chargeup.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.ma.chargeup.dao.BalanceDealDAO;
import com.pay.ma.chargeup.model.BalanceDeal;

public class BalanceDealDAOImpl extends BaseDAOImpl<BalanceDeal> implements
		BalanceDealDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ma.chargeup.dao.ChargeUpDAO#queryChargeUpInfo(java.lang.
	 * Integer)
	 */
	public List<BalanceDeal> queryChargeUpInfo(Integer chargeUpStatus) {

		List<BalanceDeal> balanceDeals = this
				.getSqlMapClientTemplate()
				.queryForList(
						this.namespace
								.concat("queryBalanceDealWithChargeUpStatus"),
						chargeUpStatus);
		if (balanceDeals == null) {
			return new ArrayList<BalanceDeal>(0);
		}
		return balanceDeals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ma.chargeup.dao.ChargeUpDAO#updateChargeUpStatus(java.lang
	 * .String, java.lang.Integer)
	 */
	public boolean updateChargeUpStatus(String serialNo, Integer dealCode,
			Integer chargeUpStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNo", serialNo);
		map.put("dealCode", dealCode);
		map.put("chargeUpStatus", chargeUpStatus);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateChargeUpStatus"), map) == 1;

	}

	@Override
	public List<BalanceDeal> queryBalanceDealInfo(String serialNo,
			Integer dealCode, Long amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNo", serialNo);
		map.put("dealCode", dealCode);
		map.put("amount", amount);
		return this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("queryBalanceDealInfo"), map);
	}

	@Override
	public List<BalanceDeal> queryBalanceDealInfoByVo(Long voucherCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("voucherCode", voucherCode);
		return super.findByCriteria("queryBalanceDealInfoByVo", map);
	}
}
