/**
 *  File: BatchRuleOperator.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleoperator;

import java.util.List;

import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;

/**
 * @author darv
 *
 */
public interface BatchRuleOperatorService {
	/**
	 * 创建操作人员信息
	 * @param batchTimeConfig
	 * @return long
	 */
	public long createBatchRuleOperator(BatchRuleOperator batchRuleOperator);
	
	/**
	 * 根据批次规则号查询相应的接收人员
	 * @param ruleConfigId
	 * @return
	 */
	public List getOperatorsByBatchId(Long ruleConfigId);
	
	/**
	 * 根据规则号删除相应的人员信息
	 * @param ruleConfigId
	 */
	public void deleteOperatorsByBatchId(Long ruleConfigId);	
}
