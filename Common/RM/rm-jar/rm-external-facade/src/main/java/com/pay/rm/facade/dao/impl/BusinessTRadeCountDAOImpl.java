package com.pay.rm.facade.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.facade.dao.BusinessTRadeCountDAO;
import com.pay.rm.facade.model.BusinessTradeCount;

public class BusinessTRadeCountDAOImpl extends BaseDAOImpl<BusinessTradeCount> 
                implements BusinessTRadeCountDAO {
	@Override
	public List<BusinessTradeCount> getBusinessTradeCount(
			BusinessTradeCount countDTO) {
		return this.findByCriteria("findBTCountBybtc", countDTO);
	}

	@Override
	public BusinessTradeCount getBusinessTradeCount(
			Map<String, Object> params) {
		return this.findObjectByCriteria("findBTCountByMap",params);
	}

	@Override
	public BusinessTradeCount createBusinessTradeCount(BusinessTradeCount count) {
		return (BusinessTradeCount) this.create("createBTCount",count);
	}

	@Override
	public boolean updateBTC(Map<String, Object> params) {
		return this.updateByMap("updateBTC", params);
	}

}
