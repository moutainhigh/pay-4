/**
 *  File: BatchRuleOperatorDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.ruleoperator;

import java.util.List;

import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 * @author darv
 *
 */
public interface BatchRuleOperatorDAO extends BaseDAO<BatchRuleOperator>{
	/**
	 * 创建操作人员
	 * @param batchRuleOperator
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