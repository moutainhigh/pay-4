package com.pay.fundout.withdraw.service.autorisk.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.autoriskcontrol.RiskManagerDAO;
import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;
import com.pay.fundout.withdraw.service.autorisk.RiskManagerService;

/**
 * 风控规则配置
 * 
 * @author Administrator
 * 
 */
public class RiskManagerServiceImpl implements RiskManagerService {

	private RiskManagerDAO riskManagerDAO;

	public void setRiskManagerDAO(RiskManagerDAO riskManagerDAO) {
		this.riskManagerDAO = riskManagerDAO;
	}

	@Override
	public void batchCreateriskmanagerRdTx(List<RmRuleDefDTO> list) {
		riskManagerDAO.batchCreateriskmanager(list);
	}

	@Override
	public List<RmRuleDefDTO> riskManagerQuery() {
		return riskManagerDAO.riskManagerQuery();
	}
	
	@Override
	public RmRuleDefDTO findRuleByRuleCode(String ruleCode) {
		return riskManagerDAO.findRuleByRuleCode(ruleCode);
	}

}
