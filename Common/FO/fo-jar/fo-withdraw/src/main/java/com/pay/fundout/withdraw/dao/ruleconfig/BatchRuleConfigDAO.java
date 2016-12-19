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

import com.pay.fundout.withdraw.dto.ruleconfig.RuleConfigQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * @author darv
 * 
 */
public interface BatchRuleConfigDAO extends BaseDAO {
	/**
	 * 创建批次规则
	 * 
	 * @param batchRuleConfig
	 * @return
	 */
	public long createBatchRuleConfig(BatchRuleConfig batchRuleConfig);

	/**
	 * 得到序列值
	 * 
	 * @return
	 */
	public Long getSeqId();

	/**
	 * 返回所有批次规则
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getBatchRuleConfigList();

	/**
	 * 查询批次规则相关信息列表
	 * 
	 * @return
	 */
	public Page<RuleConfigQueryDTO> getRuleConfigList(
			Page<RuleConfigQueryDTO> page, Map params);

	/**
	 * 根据规则号查询
	 * 
	 * @param sequenceId
	 * @return
	 */
	public RuleConfigQueryDTO getRuleConfigById(Long sequenceId);

	/**
	 * 根据ID更新批次规则
	 * 
	 * @param ruleConfig
	 */
	public void updateRuleConfigById(BatchRuleConfig ruleConfig);

	/**
	 * 通过时间配置ID获得有效的规则
	 * 
	 * @param timeId
	 * @return
	 */
	public List<BatchRuleConfig> getBatchRuleByTimeId(Long timeId);
}
