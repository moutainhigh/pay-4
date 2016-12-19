/**
 *  File: BatchRuleWithdrawBusinessService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.rulewithdrawbusiness;

import java.util.List;

import com.pay.fundout.withdraw.model.rulewithdrawbusiness.BatchRuleWithdrawBusiness;

/**
 * @author darv
 *
 */
public interface BatchRuleWithdrawBusinessService {
	/**
	 * 添加批次业务类型
	 * @param batchRuleWithdrawBusiness
	 * @return
	 */
	public long createBatchRuleWithdrawBusiness(BatchRuleWithdrawBusiness batchRuleWithdrawBusiness);
	
	/**
	 * 根据批次规则ID查询对应的业务类型
	 * @param ruleConfigId
	 * @return
	 */
	public List getBusiByBatchId(Long ruleConfigId);
	
	/**
	 * 根据批次规则号删除对应的业务类型
	 * @param ruleConfigId
	 */
	public void deleteBusiByBatchId(Long ruleConfigId);
}
