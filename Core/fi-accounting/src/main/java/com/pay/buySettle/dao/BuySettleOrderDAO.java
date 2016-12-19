package com.pay.buySettle.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.model.BuysettleOrder;

public interface BuySettleOrderDAO  extends BaseDAO<BuysettleOrder>{
	BuysettleOrder queryByCondition(BuysettleOrder params);

	BuysettleOrder queryByConditions(Map<String, Object> params);

	List<BuysettleOrder> queryByConditionsList(BuysettleOrder params);

	List<BuysettleOrder> queryByConditionsList(Map<String, Object> params);
	
	List<BuysettleOrder> queryByConditionsPage(BuysettleOrder params, Page<BuysettleOrder> page);
	
	List<BuysettleOrder> queryByConditionsPage(Map<String, Object> params, Page<BuysettleOrder> page);
	
	long QuerySeq();
	
}
