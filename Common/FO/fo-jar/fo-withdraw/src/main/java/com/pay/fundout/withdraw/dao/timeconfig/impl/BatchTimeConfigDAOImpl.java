/**
 *  File: BatchTimeConfigDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.timeconfig.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.timeconfig.BatchTimeConfigDAO;
import com.pay.fundout.withdraw.dto.timeconfig.BatchTimeConfigDTO;
import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author darv
 * 
 */
public class BatchTimeConfigDAOImpl extends BaseDAOImpl<BatchTimeConfig>
		implements BatchTimeConfigDAO {

	@Override
	public long createBatchTimeConfig(BatchTimeConfig batchTimeConfig) {
		return (Long) this.create("create", batchTimeConfig);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getSequenceIdByWeekDayListAndType(Map params) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getSequenceIdByWeekDayListAndType"), params);
	}

	@Override
	public BatchTimeConfigDTO getTimeConfigById(Long timeConfigId) {
		return (BatchTimeConfigDTO) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getTimeConfigById"), timeConfigId);
	}

	@Override
	public void updateTimeConfigById(BatchTimeConfig timeConfig) {
		this.update(timeConfig);
	}

	/**
	 * 通过状态查询所有 批次生成时间配置表的数据 0 为无效，1 有效
	 * 
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchTimeConfig> getBatchTimeConfig(Integer status) {
		Map params = new HashMap();
		params.put("status", status);
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("findBySelective"), params);
	}
}