/**
 *  File: BatchRuleOperatorDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.ruleoperator.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.ruleoperator.BatchRuleOperatorDAO;
import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author darv
 * 
 */
public class BatchRuleOperatorDAOImpl extends BaseDAOImpl<BatchRuleOperator>
		implements BatchRuleOperatorDAO {

	@Override
	public long createBatchRuleOperator(BatchRuleOperator batchRuleOperator) {
		this.create("create", batchRuleOperator);
		return batchRuleOperator.getSequenceId();
	}

	@Override
	public List getOperatorsByBatchId(Long ruleConfigId) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("getOperatorsByBatchId"), ruleConfigId);
	}

	@Override
	public void deleteOperatorsByBatchId(Long ruleConfigId) {
		getSqlMapClientTemplate().delete(
				namespace.concat("deleteOperatorsByBatchId"), ruleConfigId);
	}

}