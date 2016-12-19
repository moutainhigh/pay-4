package com.pay.fundout.withdraw.dao.autoriskcontrol.impl;

import java.util.List;

import com.pay.fundout.withdraw.dao.autoriskcontrol.RiskManagerDAO;
import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class RiskManagerDAOImpl extends BaseDAOImpl<RmRuleDefDTO> implements
		RiskManagerDAO {

	@Override
	public List<RmRuleDefDTO> riskManagerQuery() {
		return super.findByQuery("riskmanager.queryrmruledefdto", null);
	}

	@Override
	public long createriskmanager(RmRuleDefDTO dto) {
		return (Long) super.create("riskmanager.createriskmanager", dto);
	}

	public void batchCreateriskmanager(List<RmRuleDefDTO> list) {
		try {
			super.delete("riskmanager.delete", null);
			super.batchCreate("riskmanager.createriskmanager", list);
		} catch (Exception e) {
			logger.error("风控规则配置保存异常", e);
		}
	}

	@Override
	public RmRuleDefDTO findRuleByRuleCode(String ruleCode) {
		return (RmRuleDefDTO) super.getSqlMapClientTemplate().queryForObject(
				"riskmanager.findrulebyrulecode", ruleCode);
	}

}
