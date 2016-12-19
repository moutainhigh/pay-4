/**
 *  File: BatchTimeConfigDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.timeconfig;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.timeconfig.BatchTimeConfigDTO;
import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 * @author darv
 * 
 */
public interface BatchTimeConfigDAO extends BaseDAO<BatchTimeConfig> {
	/**
	 * 创建批次时间配置
	 * 
	 * @param batchTimeConfig
	 * @return long
	 */
	public long createBatchTimeConfig(BatchTimeConfig batchTimeConfig);

	/**
	 * 通过weekdayList和timeType查找对象的sequence_id
	 * 
	 * @param params
	 * @return
	 */
	public Long getSequenceIdByWeekDayListAndType(Map params);

	/**
	 * 根据时间ID找时间配置
	 * 
	 * @param timeConfigId
	 * @return
	 */
	public BatchTimeConfigDTO getTimeConfigById(Long timeConfigId);

	/**
	 * 根据时间号更新时间配置
	 * 
	 * @param timeConfig
	 */
	public void updateTimeConfigById(BatchTimeConfig timeConfig);

	/**
	 * 通过状态查询所有 批次生成时间配置表的数据 0 为无效，1 有效
	 * 
	 * @param status
	 * @return
	 */
	public List<BatchTimeConfig> getBatchTimeConfig(Integer status);

}