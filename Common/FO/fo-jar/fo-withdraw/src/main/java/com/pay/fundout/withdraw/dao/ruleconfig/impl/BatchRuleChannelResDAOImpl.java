/**
 *  File: WithDrawRuleConfigDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.ruleconfig.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.ruleconfig.BatchRuleChannelResDAO;
import com.pay.fundout.withdraw.dto.ruleconfig.BatchRuleChannelQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleChannelRes;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author darv
 * 
 */
public class BatchRuleChannelResDAOImpl extends BaseDAOImpl implements
		BatchRuleChannelResDAO {

	@Override
	public Page<BatchRuleChannelQueryDTO> getBatchRuleChannelResList(
			Page<BatchRuleChannelQueryDTO> page, Map params) {
		return (Page<BatchRuleChannelQueryDTO>) this.findByQuery(
				"batchrulechannelres.getBatchRuleChannelResList", page, params);
	}

	@Override
	public Long insert(BatchRuleChannelRes res) {
		return (Long) this.create("batchrulechannelres.insert", res);
	}

	@Override
	public void updateBatchRuleChannelResById(BatchRuleChannelRes res) {
		getSqlMapClientTemplate().update(
				"batchrulechannelres.updateBatchRuleChannelResById", res);
	}

	@Override
	public List getResChannelList(Long ruleConfigId) {
		return getSqlMapClientTemplate().queryForList(
				"batchrulechannelres.getResChannelList", ruleConfigId);
	}

}
