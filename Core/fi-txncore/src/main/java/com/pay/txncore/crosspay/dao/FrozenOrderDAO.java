package com.pay.txncore.crosspay.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.FrozenOrder;

public interface FrozenOrderDAO  extends BaseDAO<FrozenOrder>{
	
	boolean updateFrozenOrder(String sql,Map<String, Object> conMap);
	
	void createFrozenOrder(String sql,Map<String,Object> conMap);
}