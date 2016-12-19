package com.pay.fundout.withdraw.service.autorisk;

import java.util.List;

import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;

/**
 * 风控规则配置
 * 
 * @author Administrator
 * 
 */
public interface RiskManagerService {

	/**
	 * 批量创建出款风控规则
	 * @param list
	 */
	void batchCreateriskmanagerRdTx(List<RmRuleDefDTO> list);

	/**
	 * 查询全部出款风控规则
	 * @return
	 */
	List<RmRuleDefDTO> riskManagerQuery();
	
	/**
	 * 根据规则代码查找规则实体
	 * @param ruleCode
	 */
	RmRuleDefDTO findRuleByRuleCode(String ruleCode);

}
