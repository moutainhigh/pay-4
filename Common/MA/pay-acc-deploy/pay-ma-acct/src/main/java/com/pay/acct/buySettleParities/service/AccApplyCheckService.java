package com.pay.acct.buySettleParities.service;

import java.util.List;
import java.util.Map;

import com.pay.acct.buySettleParities.model.AccApplyCheck;
import com.pay.inf.dao.Page;

public interface AccApplyCheckService {
	List<AccApplyCheck> queryConditions(AccApplyCheck accApplyCheck);
	List<AccApplyCheck> queryByCriteria(Map<String, Object> params);
	long createAccApplyCheck(AccApplyCheck accApplyCheck);
	List<AccApplyCheck> queryByCriteria(Map<String, Object> params,
			Page<Object> page);
	void check(List<AccApplyCheck> accApplyChecks);
	Integer queryByCriteriaCount(Map<String, Object> params);
}
