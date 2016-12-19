/**
 *  File: WithDrawRuleConfigService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleconfig;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.ruleconfig.RuleConfigQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * @author darv
 * 
 */
public interface BatchRuleConfigService {
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
	public Map<String,String> getRuleConfigMap(List list);

	/**
	 * 查询批次规则相关信息列表
	 * 
	 * @return
	 */
	public Page<RuleConfigQueryDTO> getRuleConfigList(Page<RuleConfigQueryDTO> page, Map params);

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

	public void createRuleConfigRdTx(BatchRuleConfig ruleConfig, BatchTimeConfig timeConfig, List operatorList)
			throws PossException;

	public void updateRuleConfigRdTx(BatchRuleConfig ruleConfig, BatchTimeConfig timeConfig, List operatorList)
			throws PossException;

	/**
	 * 通过时间配置ID获得规则
	 * 
	 * @param timeId
	 * @return
	 */
	public List<BatchRuleConfig> getBatchRuleByTimeId(Long timeId);

}
