package com.pay.acct.buySettleParities.dao;

import java.util.List;
import java.util.Map;

import com.pay.acct.buySettleParities.model.AccApplyCheck;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface AccApplyCheckDao extends BaseDAO<AccApplyCheck>{
	List<AccApplyCheck> queryConditions(AccApplyCheck accApplyCheck);
	List<AccApplyCheck> queryByCriteria(Map<String, Object> params);
	List<AccApplyCheck> queryByCriteria(Map<String, Object> params,
			Page<Object> page);
	Integer queryByCriteriaCount(Map<String, Object> params);
}
