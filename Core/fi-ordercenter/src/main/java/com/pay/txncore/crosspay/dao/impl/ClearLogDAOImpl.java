package com.pay.txncore.crosspay.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.ClearLogDAO;
import com.pay.txncore.crosspay.model.ClearLog;

public class ClearLogDAOImpl extends BaseDAOImpl<ClearLog> implements
		ClearLogDAO {

	@Override
	public List<ClearLog> queryClearLog(Map<String, Object> paramMap,
			int pageSize, int pageNo) throws Exception {
		int offset = (pageNo - 1) * pageSize;
		List<ClearLog> list = null;
		list = getSqlMapClientTemplate().queryForList(
				"CLEAR_LOG.queryClearLog", paramMap, offset, pageSize);
		return list;
	}

	@Override
	public Integer countQueryClearLog(Map<String, Object> paramMap)
			throws Exception {
		Integer i = (Integer) getSqlMapClientTemplate().queryForObject(
				"CLEAR_LOG.queryClearLogCount", paramMap);
		return i;
	}
}