/**
 *  File: BatchRuleWithdrawBankServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.rulewithdrawbank.impl;

import java.util.List;

import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.withdraw.dao.rulewithdrawbank.BatchRuleWithdrawBankDAO;
import com.pay.fundout.withdraw.model.rulewithdrawbank.BatchRuleWithdrawBank;
import com.pay.fundout.withdraw.service.rulewithdrawbank.BatchRuleWithdrawBankService;

/**
 * @author darv
 * 
 */
public class BatchRuleWithdrawBankServiceImpl implements BatchRuleWithdrawBankService {
	private BatchRuleWithdrawBankDAO batchRuleWithdrawBankDao;

	public void setBatchRuleWithdrawBankDao(BatchRuleWithdrawBankDAO batchRuleWithdrawBankDao) {
		this.batchRuleWithdrawBankDao = batchRuleWithdrawBankDao;
	}

	@Override
	public long createbatchRuleWithdrawBank(BatchRuleWithdrawBank batchRuleWithdrawBank) {
		return this.batchRuleWithdrawBankDao.createbatchRuleWithdrawBank(batchRuleWithdrawBank);
	}

	@Override
	public void deleteBankByBatchId(Long ruleConfigId) {
		this.batchRuleWithdrawBankDao.deleteBankByBatchId(ruleConfigId);
	}

	@Override
	public List getBankByBatchId(Long ruleConfigId) {
		return this.batchRuleWithdrawBankDao.getBankByBatchId(ruleConfigId);
	}
	
	/**
	 * 通过规则ID查找符合规则并有效的银行
	 * @param ruleId
	 * @return
	 */
	@Override
	public List<BatchRuleWithdrawBank> getBatchBankByRuleId(Long ruleId) {
		List<BatchRuleWithdrawBank> list =  this.batchRuleWithdrawBankDao.getBatchBankByRuleId(ruleId);
		return list;
	}

	@Override
	public List<FundoutChannel> getFundoutChannelByRuleId(Long ruleId) {
		return this.batchRuleWithdrawBankDao.getFundoutChannelByRuleId(ruleId);
	}
}
