/**
 *  File: BatchRuleWithdrawBusinessDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.rulewithdrawbusiness.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.rulewithdrawbusiness.BatchRuleWithdrawBusinessDAO;
import com.pay.fundout.withdraw.model.rulewithdrawbusiness.BatchRuleWithdrawBusiness;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author darv
 * 
 */
public class BatchRuleWithdrawBusinessDAOImpl extends
		BaseDAOImpl<BatchRuleWithdrawBusiness> implements
		BatchRuleWithdrawBusinessDAO {

	@Override
	public long createBatchRuleWithdrawBusiness(
			BatchRuleWithdrawBusiness batchRuleWithdrawBusiness) {
		return (Long) this.create("create",
				batchRuleWithdrawBusiness);
	}

	@Override
	public List getBusiByBatchId(Long ruleConfigId) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("getBusiByBatchId"), ruleConfigId);
	}

	@Override
	public void deleteBusiByBatchId(Long ruleConfigId) {
		getSqlMapClientTemplate().delete(
				namespace.concat("deleteBusiByBatchId"), ruleConfigId);
	}
}