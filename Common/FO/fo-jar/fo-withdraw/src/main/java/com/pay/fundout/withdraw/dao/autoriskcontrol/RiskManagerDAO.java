package com.pay.fundout.withdraw.dao.autoriskcontrol;

import java.util.List;

import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;
import com.pay.inf.dao.BaseDAO;

/**
 * 风控规则配置
 * 
 * @author Administrator
 * 
 */
public interface RiskManagerDAO extends BaseDAO<RmRuleDefDTO> {

	List<RmRuleDefDTO> riskManagerQuery();

	long createriskmanager(RmRuleDefDTO dto);

	void batchCreateriskmanager(List<RmRuleDefDTO> list);

	RmRuleDefDTO findRuleByRuleCode(String ruleCode);

}
