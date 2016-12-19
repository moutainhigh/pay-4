/**
 *  File: BatchRuleWithdrawBusinessServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.rulewithdrawbusiness.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.rulewithdrawbusiness.BatchRuleWithdrawBusinessDAO;
import com.pay.fundout.withdraw.model.rulewithdrawbusiness.BatchRuleWithdrawBusiness;
import com.pay.fundout.withdraw.service.rulewithdrawbusiness.BatchRuleWithdrawBusinessService;

/**
 * @author darv
 * 
 */
public class BatchRuleWithdrawBusinessServiceImpl implements BatchRuleWithdrawBusinessService {
	private BatchRuleWithdrawBusinessDAO batchRuleWithdrawBusinessDao;

	public void setBatchRuleWithdrawBusinessDao(BatchRuleWithdrawBusinessDAO batchRuleWithdrawBusinessDao) {
		this.batchRuleWithdrawBusinessDao = batchRuleWithdrawBusinessDao;
	}

	@Override
	public long createBatchRuleWithdrawBusiness(BatchRuleWithdrawBusiness batchRuleWithdrawBusiness) {
		return this.batchRuleWithdrawBusinessDao.createBatchRuleWithdrawBusiness(batchRuleWithdrawBusiness);
	}

	@Override
	public List getBusiByBatchId(Long ruleConfigId) {
		return this.batchRuleWithdrawBusinessDao.getBusiByBatchId(ruleConfigId);
	}

	@Override
	public void deleteBusiByBatchId(Long ruleConfigId) {
		this.batchRuleWithdrawBusinessDao.deleteBusiByBatchId(ruleConfigId);
	}

}
