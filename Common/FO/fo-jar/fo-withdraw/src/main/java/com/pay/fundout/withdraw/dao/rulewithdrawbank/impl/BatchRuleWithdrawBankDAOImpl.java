/**
 *  File: BatchRuleWithdrawBankDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.rulewithdrawbank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.withdraw.dao.rulewithdrawbank.BatchRuleWithdrawBankDAO;
import com.pay.fundout.withdraw.model.rulewithdrawbank.BatchRuleWithdrawBank;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author darv
 * 
 */
public class BatchRuleWithdrawBankDAOImpl extends BaseDAOImpl implements
		BatchRuleWithdrawBankDAO {

	@Override
	public long createbatchRuleWithdrawBank(
			BatchRuleWithdrawBank batchRuleWithdrawBank) {
		return (Long) this.create("create", batchRuleWithdrawBank);
	}

	@Override
	public List getBankByBatchId(Long ruleConfigId) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("getBankByBatchId"), ruleConfigId);
	}

	@Override
	public void deleteBankByBatchId(Long ruleConfigId) {
		getSqlMapClientTemplate().delete(
				namespace.concat("deleteBankByBatchId"), ruleConfigId);
	}

	/**
	 * 通过规则ID查找符合规则并有效的银行
	 * 
	 * @param ruleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchRuleWithdrawBank> getBatchBankByRuleId(Long ruleId) {
		Map params = new HashMap();
		params.put("batchRuleId", ruleId);
		params.put("status", 1);
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("findBySelective"), params);
	}

	@Override
	public List<FundoutChannel> getFundoutChannelByRuleId(Long ruleId) {
		return this.findByQuery("getChannelInfoByRuleKy", ruleId);
	}

}