/**
 *  File: WithDrawRuleConfigDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.ruleconfig.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.ruleconfig.BatchRuleConfigDAO;
import com.pay.fundout.withdraw.dto.ruleconfig.RuleConfigQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author darv
 * 
 */
public class BatchRuleConfigDAOImpl extends BaseDAOImpl implements
		BatchRuleConfigDAO {

	@Override
	public long createBatchRuleConfig(BatchRuleConfig batchRuleConfig) {
		this.create("create", batchRuleConfig);
		return batchRuleConfig.getSequenceId();
		
	}

	@Override
	public Long getSeqId() {
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("getSeqId"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBatchRuleConfigList() {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("findBatchRuleConfigList"));
	}

	@Override
	public Page<RuleConfigQueryDTO> getRuleConfigList(
			Page<RuleConfigQueryDTO> page, Map params) {
		return (Page<RuleConfigQueryDTO>) findByQuery("getRuleConfigList",
				page, params);
	}

	@Override
	public RuleConfigQueryDTO getRuleConfigById(Long sequenceId) {
		return (RuleConfigQueryDTO) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getRuleConfigById"), sequenceId);
	}

	@Override
	public void updateRuleConfigById(BatchRuleConfig ruleConfig) {
		this.update(ruleConfig);
	}

	/**
	 * 通过时间配置ID获得有效的规则
	 * 
	 * @param timeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchRuleConfig> getBatchRuleByTimeId(Long timeId) {
		Map<String, Object> params = new HashMap();
		params.put("batchTimeConfId", timeId);
		params.put("status", 1);
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("findBySelective"), params);
	}
}
