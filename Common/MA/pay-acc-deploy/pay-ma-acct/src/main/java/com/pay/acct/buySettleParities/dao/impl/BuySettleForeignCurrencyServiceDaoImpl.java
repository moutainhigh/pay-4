package com.pay.acct.buySettleParities.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.acct.buySettleParities.dao.BuySettleForeignCurrencyServiceDao;
import com.pay.acct.buySettleParities.model.BuysettlePoundageConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class BuySettleForeignCurrencyServiceDaoImpl extends BaseDAOImpl  implements BuySettleForeignCurrencyServiceDao{

	@Override
	public List<BuysettlePoundageConfig> queryBuySettleForeignCurrency(Map<String, Object> param,
			Page<Object> page) {
		return this.findByCriteria(param, page);
	}

	@Override
	public void addBuySettleForeignCurrency(Map<String, Object> param) {
		this.create(param);
	}

	@Override
	public void deleteBuySettleForeignCurrency(String id) {
		this.delete(id);
	}

	@Override
	public void updateBuySettleForeignCurrency(Map<String, Object> param) {
		this.update(param);
	}

	@Override
	public BuysettlePoundageConfig queryBuySettleForeignCurrency(Map<String, Object> param) {
		
		return (BuysettlePoundageConfig) getSqlMapClientTemplate().queryForObject(this.namespace.concat("countByCriteriaBypartnerId"),param);
	}

}
