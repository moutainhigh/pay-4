/**
 *  File: BatchTimeConfigServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.timeconfig.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.timeconfig.BatchTimeConfigDAO;
import com.pay.fundout.withdraw.dto.timeconfig.BatchTimeConfigDTO;
import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.fundout.withdraw.service.timeconfig.BatchTimeConfigService;
import com.pay.pe.dto.BeanUtils;

/**
 * @author darv
 * 
 */
public class BatchTimeConfigServiceImpl implements BatchTimeConfigService {
	private BatchTimeConfigDAO batchTimeConfigDAO;

	public void setBatchTimeConfigDAO(BatchTimeConfigDAO batchTimeConfigDAO) {
		this.batchTimeConfigDAO = batchTimeConfigDAO;
	}

	@Override
	public long createBatchTimeConfig(BatchTimeConfig batchTimeConfig) {
		this.batchTimeConfigDAO.create(batchTimeConfig);
		return batchTimeConfig.getSequenceId();
	}

	@Override
	public BatchTimeConfigDTO getTimeConfigById(Long timeConfigId) {
		return this.batchTimeConfigDAO.getTimeConfigById(timeConfigId);
	}

	@Override
	public void updateTimeConfigById(BatchTimeConfig timeConfig) {
		this.batchTimeConfigDAO.updateTimeConfigById(timeConfig);
	}

	/**
	 * 通过状态查询所有 批次生成时间配置表的数据 0 为无效，1 有效
	 * 
	 * @param status
	 * @return
	 */
	@Override
	public List<BatchTimeConfigDTO> getBatchTimeConfig(Integer status) {

		List<BatchTimeConfig> list = this.batchTimeConfigDAO.getBatchTimeConfig(status);

		List<BatchTimeConfigDTO> listDtos = new ArrayList<BatchTimeConfigDTO>();
		if (list != null && list.size() > 0) {
			for (BatchTimeConfig mode : list) {
				BatchTimeConfigDTO dto = new BatchTimeConfigDTO();
				BeanUtils.copyProperties(mode, dto);
				listDtos.add(dto);
			}
		}
		return listDtos;
	}

	@Override
	public Long getSequenceIdByWeekDayListAndType(Map params) {
		return this.batchTimeConfigDAO.getSequenceIdByWeekDayListAndType(params);
	}
}
