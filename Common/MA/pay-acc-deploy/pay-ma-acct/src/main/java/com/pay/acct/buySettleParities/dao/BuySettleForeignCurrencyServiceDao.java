package com.pay.acct.buySettleParities.dao;

import java.util.List;
import java.util.Map;

import com.pay.acct.buySettleParities.model.BuysettlePoundageConfig;
import com.pay.inf.dao.Page;

public interface BuySettleForeignCurrencyServiceDao {

	List<BuysettlePoundageConfig> queryBuySettleForeignCurrency(Map<String, Object> param, Page<Object> page);

	void addBuySettleForeignCurrency(Map<String, Object> param);

	void deleteBuySettleForeignCurrency(String id);

	void updateBuySettleForeignCurrency(Map<String, Object> param);
	
	BuysettlePoundageConfig queryBuySettleForeignCurrency(Map<String, Object> param);
}
