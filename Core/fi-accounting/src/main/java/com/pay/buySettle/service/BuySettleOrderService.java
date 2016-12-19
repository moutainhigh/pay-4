package com.pay.buySettle.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.BuysettleOrder;

public interface BuySettleOrderService {
	BuysettleOrder queryByCondition(BuysettleOrder params);

	BuysettleOrder queryByConditions(Map<String, Object> params);

	List<BuysettleOrder> queryByConditionsList(BuysettleOrder params);

	List<BuysettleOrder> queryByConditionsList(Map<String, Object> params);
	
	List<BuysettleOrder> queryByConditionsPage(BuysettleOrder params, Page<BuysettleOrder> page);
	
	List<BuysettleOrder> queryByConditionsPage(Map<String, Object> params, Page<BuysettleOrder> page);
	
	Object create(BuysettleOrder pojo);
}
