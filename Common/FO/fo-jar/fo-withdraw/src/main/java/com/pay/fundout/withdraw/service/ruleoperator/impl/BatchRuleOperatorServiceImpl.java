/**
 *  File: BatchRuleOperatorServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleoperator.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.ruleoperator.BatchRuleOperatorDAO;
import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;
import com.pay.fundout.withdraw.service.ruleoperator.BatchRuleOperatorService;

/**
 * @author darv
 * 
 */
public class BatchRuleOperatorServiceImpl implements BatchRuleOperatorService {
	private BatchRuleOperatorDAO batchRuleOperatorDao;

	public void setBatchRuleOperatorDao(BatchRuleOperatorDAO batchRuleOperatorDao) {
		this.batchRuleOperatorDao = batchRuleOperatorDao;
	}

	@Override
	public long createBatchRuleOperator(BatchRuleOperator batchRuleOperator) {
		this.batchRuleOperatorDao.createBatchRuleOperator(batchRuleOperator);
		return batchRuleOperator.getSequenceId();
	}

	@Override
	public void deleteOperatorsByBatchId(Long ruleConfigId) {
		this.batchRuleOperatorDao.deleteOperatorsByBatchId(ruleConfigId);
	}

	@Override
	public List getOperatorsByBatchId(Long ruleConfigId) {
		return this.batchRuleOperatorDao.getOperatorsByBatchId(ruleConfigId);
	}
}
