/**
 *  File: BatchRuleWithdrawBankService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.rulewithdrawbank;

import java.util.List;

import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.withdraw.model.rulewithdrawbank.BatchRuleWithdrawBank;

/**
 * @author darv
 *
 */
public interface BatchRuleWithdrawBankService {
	/**
	 * 添加批次关联银行
	 * @param batchRuleWithdrawBank
	 * @return
	 */
	public long createbatchRuleWithdrawBank(BatchRuleWithdrawBank batchRuleWithdrawBank);
	
	/**
	 * 根据批次规则号查找对应的关联银行
	 * @param ruleConfigId
	 * @return
	 */
	public List getBankByBatchId(Long ruleConfigId);
	
	/**
	 * 根据批次规则号删除对应的关联银行
	 * @param ruleConfigId
	 */
	public void deleteBankByBatchId(Long ruleConfigId);
	
	/**
	 * 通过规则ID查找符合规则并有效的银行
	 * @param ruleId
	 * @return
	 */
	public List<BatchRuleWithdrawBank> getBatchBankByRuleId(Long ruleId);
	/**
	 * 通过规则ID查找符合规则并有效的出款渠道
	 * @param ruleId
	 * @return
	 */
	public List<FundoutChannel> getFundoutChannelByRuleId(Long ruleId);
	
	
	
}
