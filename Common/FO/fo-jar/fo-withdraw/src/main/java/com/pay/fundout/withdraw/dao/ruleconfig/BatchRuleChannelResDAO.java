/**
 *  File: WithDrawRuleConfigDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.ruleconfig;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.ruleconfig.BatchRuleChannelQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleChannelRes;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * @author darv
 * 
 */
public interface BatchRuleChannelResDAO extends BaseDAO {
	/**
	 * 创建规则和渠道的关联
	 * 
	 * @param res
	 * @return
	 */
	public Long insert(BatchRuleChannelRes res);

	/**
	 * 查询规则和渠道的关联列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<BatchRuleChannelQueryDTO> getBatchRuleChannelResList(
			Page<BatchRuleChannelQueryDTO> page, Map params);

	/**
	 * 更新规则和渠道的关联
	 * 
	 * @param res
	 */
	public void updateBatchRuleChannelResById(BatchRuleChannelRes res);

	/**
	 * 查询某个批次规则没有关联的业务渠道列表
	 * 
	 * @param ruleConfigId
	 * @return
	 */
	public List getResChannelList(Long ruleConfigId);
}
