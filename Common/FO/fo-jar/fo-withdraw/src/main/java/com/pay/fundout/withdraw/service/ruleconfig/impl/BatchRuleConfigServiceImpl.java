/**
 *  File: WithDrawRuleConfigServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleconfig.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.ruleconfig.BatchRuleConfigDAO;
import com.pay.fundout.withdraw.dto.ruleconfig.RuleConfigQueryDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;
import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleConfigService;
import com.pay.fundout.withdraw.service.ruleoperator.BatchRuleOperatorService;
import com.pay.fundout.withdraw.service.rulewithdrawbank.BatchRuleWithdrawBankService;
import com.pay.fundout.withdraw.service.rulewithdrawbusiness.BatchRuleWithdrawBusinessService;
import com.pay.fundout.withdraw.service.timeconfig.BatchTimeConfigService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import common.Logger;

/**
 * @author darv
 * 
 */
public class BatchRuleConfigServiceImpl implements BatchRuleConfigService {
	private BatchRuleConfigDAO batchRuleConfigDao;

	private BatchRuleOperatorService batchRuleOperatorService;

	private BatchTimeConfigService batchTimeConfigService;

	private BatchRuleWithdrawBankService batchRuleWithdrawBankService;

	private BatchRuleWithdrawBusinessService batchRuleWithdrawBusinessService;

	public void setBatchRuleOperatorService(BatchRuleOperatorService batchRuleOperatorService) {
		this.batchRuleOperatorService = batchRuleOperatorService;
	}

	public void setBatchTimeConfigService(BatchTimeConfigService batchTimeConfigService) {
		this.batchTimeConfigService = batchTimeConfigService;
	}

	public void setBatchRuleWithdrawBankService(BatchRuleWithdrawBankService batchRuleWithdrawBankService) {
		this.batchRuleWithdrawBankService = batchRuleWithdrawBankService;
	}

	public void setBatchRuleWithdrawBusinessService(BatchRuleWithdrawBusinessService batchRuleWithdrawBusinessService) {
		this.batchRuleWithdrawBusinessService = batchRuleWithdrawBusinessService;
	}

	public void setBatchRuleConfigDao(BatchRuleConfigDAO batchRuleConfigDao) {
		this.batchRuleConfigDao = batchRuleConfigDao;
	}

	@Override
	public long createBatchRuleConfig(BatchRuleConfig batchRuleConfig) {
		return this.batchRuleConfigDao.createBatchRuleConfig(batchRuleConfig);
	}

	@Override
	public Long getSeqId() {
		return this.batchRuleConfigDao.getSeqId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBatchRuleConfigList() {
		return this.batchRuleConfigDao.getBatchRuleConfigList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<RuleConfigQueryDTO> getRuleConfigList(Page<RuleConfigQueryDTO> page, Map params) {
		return this.batchRuleConfigDao.getRuleConfigList(page, params);
	}

	@Override
	public RuleConfigQueryDTO getRuleConfigById(Long sequenceId) {
		return this.batchRuleConfigDao.getRuleConfigById(sequenceId);
	}

	@Override
	public void updateRuleConfigById(BatchRuleConfig ruleConfig) {
		this.batchRuleConfigDao.updateRuleConfigById(ruleConfig);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createRuleConfigRdTx(BatchRuleConfig ruleConfig, BatchTimeConfig timeConfig, List operatorList)
			throws PossException {
		try {
			if (timeConfig.getSequenceId() == null) {
				this.batchTimeConfigService.createBatchTimeConfig(timeConfig);
				ruleConfig.setBatchTimeConfId(timeConfig.getSequenceId());
			}
			this.batchRuleConfigDao.createBatchRuleConfig(ruleConfig);
			for (int i = 0; i < operatorList.size(); i++) {
				BatchRuleOperator oper = (BatchRuleOperator) operatorList.get(i);
				this.batchRuleOperatorService.createBatchRuleOperator(oper);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("--------------");
			throw new PossException(e.getMessage(), ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateRuleConfigRdTx(BatchRuleConfig ruleConfig, BatchTimeConfig timeConfig, List operatorList)
			throws PossException {
		try {
			if (timeConfig.getSequenceId() == null) {
				timeConfig.setSequenceId(ruleConfig.getBatchTimeConfId());
			} else {
				ruleConfig.setBatchTimeConfId(timeConfig.getSequenceId());
			}
			this.batchTimeConfigService.updateTimeConfigById(timeConfig);
			this.batchRuleConfigDao.updateRuleConfigById(ruleConfig);
			this.batchRuleOperatorService.deleteOperatorsByBatchId(ruleConfig.getSequenceId());
			for (int i = 0; i < operatorList.size(); i++) {
				BatchRuleOperator oper = (BatchRuleOperator) operatorList.get(i);
				this.batchRuleOperatorService.createBatchRuleOperator(oper);
			}
		} catch (Exception e) {
			throw new PossException(e.getMessage(), ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}
	}

	/**
	 * 通过时间配置ID获得有效的规则
	 * 
	 * @param timeId
	 * @return
	 */
	@Override
	public List<BatchRuleConfig> getBatchRuleByTimeId(Long timeId) {
		List<BatchRuleConfig> list = this.batchRuleConfigDao.getBatchRuleByTimeId(timeId);
		return list;
	}

	@Override
	public Map<String, String> getRuleConfigMap(List list) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			BatchRuleConfig config = (BatchRuleConfig) list.get(i);
			map.put(config.getSequenceId().toString(), config.getBatchRuleDesc());
		}
		return map;
	}
}
