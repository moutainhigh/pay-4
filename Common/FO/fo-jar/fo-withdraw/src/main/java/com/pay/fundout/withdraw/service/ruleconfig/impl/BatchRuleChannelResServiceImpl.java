/**
 *  File: BatchRuleChannelResServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleconfig.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.withdraw.dao.ruleconfig.BatchRuleChannelResDAO;
import com.pay.fundout.withdraw.dto.ruleconfig.BatchRuleChannelQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleChannelRes;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleChannelResService;
import com.pay.inf.dao.Page;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class BatchRuleChannelResServiceImpl implements BatchRuleChannelResService {
	private BatchRuleChannelResDAO batchRuleChannelResDAO;

	private FundoutChannelService fundoutChannelService;

	public void setFundoutChannelService(FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

	public void setBatchRuleChannelResDAO(BatchRuleChannelResDAO batchRuleChannelResDAO) {
		this.batchRuleChannelResDAO = batchRuleChannelResDAO;
	}

	@Override
	public Page<BatchRuleChannelQueryDTO> getBatchRuleChannelResList(Page<BatchRuleChannelQueryDTO> page, Map params) {
		return batchRuleChannelResDAO.getBatchRuleChannelResList(page, params);
	}

	@Override
	public Long insert(BatchRuleChannelRes res) {
		return batchRuleChannelResDAO.insert(res);
	}

	@Override
	public void updateBatchRuleChannelResById(BatchRuleChannelRes res) {
		batchRuleChannelResDAO.updateBatchRuleChannelResById(res);
	}

	@Override
	public List getResChannelList(Long ruleConfigId) {
		return batchRuleChannelResDAO.getResChannelList(ruleConfigId);
	}

	@Override
	public boolean generatorRuleConfigChannel(Map params) {
		try {
			String withdrawBankCode=params.get("withdrawBankCode").toString();
			String withdrawType=params.get("withdrawType").toString();
			Long[] busiType=(Long[]) params.get("busiType");
			
			Map args=new HashMap();
			args.put("withdrawBankCode", withdrawBankCode);
			args.put("withdrawType", withdrawType);
			
			//for(Long bt : busiType){
				//args.put("busiType", bt);
				String channelId = fundoutChannelService.getChannelId(args);
				BatchRuleChannelRes res = new BatchRuleChannelRes();
				res.setChannelKy(channelId);
				res.setRuleKy(params.get("ruleKy").toString());
				res.setCreatedBy(params.get("userName").toString());
				res.setCreatedDate(new Date());
				res.setState(1);
				batchRuleChannelResDAO.insert(res);
			//}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
