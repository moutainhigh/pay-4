package com.pay.txncore.crosspay.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.ClearLog;

public interface ClearLogDAO extends BaseDAO<ClearLog>  {
	
	/**
	 * 分页查询清算历史记录
	 * @param paramMap
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public List<ClearLog> queryClearLog(Map<String, Object> paramMap,
			int pageSize, int pageNo) throws Exception;
	
	/**
	 * 统计清算记录
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Integer countQueryClearLog(Map<String, Object> paramMap)
			throws Exception;
}