/**
 *  File: WithdrawRuleService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15     jason.li    Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.withdrawrule;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.bank.WithdrawBankConfigDTO;
import com.pay.fundout.withdraw.dto.busitype.WithdrawBusinessDTO;
import com.pay.fundout.withdraw.dto.rule.WithdrawRuleDTO;
import com.pay.fundout.withdraw.dto.type.WithdrawTypeDTO;
import com.pay.fundout.withdraw.model.bank.WithdrawBankConfig;
import com.pay.fundout.withdraw.model.busitype.WithdrawBusiness;
import com.pay.fundout.withdraw.model.rule.WithdrawRule;
import com.pay.fundout.withdraw.model.type.WithdrawType;

/**
 * @author jason.li
 *
 */
public interface WithdrawRuleService {
	/**
	 * 获得银行出款规则
	 * @param map 里面参数如下(也是取参数的KEY)：
	 * @param destBankId 目的行;
	 * @param amount金额;
	 * @param mtype交易类型;
	 * @param bankAccoutType银行账号类型;
	 * @return WithdrawRule
	 */
	public WithdrawRule getRule(Map<String,String> map);
	/**
	 * 获得出款方式
	 * @param map 里面参数如下(也是取参数的KEY)：
	 * @param destBankId 目的行;
	 * @param amount金额;
	 * @param mtype交易类型;
	 * @param bankAccoutType银行账号类型;
	 * @return WithdrawRule
	 */
	public int getWithdrawType(Map<String,String> map);	
	/**
	 * 创建出款规则
	 * @param wr
	 * @return
	 */
	public long createWithdrawRule(WithdrawRule wr);
	/**
	 * 更改规则状态
	 * @param status
	 * @return
	 */
	public boolean updateWithdrawRule(WithdrawRule wr);
	/**
	 * String ruleName,int toBankId,int fromBankId,int bankAccountType,int status,int wrType,int priority 7个参数
	 * @param curPage
	 * @param map
	 * @return
	 */
	//public Page<WithdrawRuleDTO> searchRules(Page<WithdrawRuleDTO> curPage,Map<String,String> map);
	public List<WithdrawRuleDTO> searchRules(Map<String,String> map);
	/**
	 * 出款方式
	 * @param wt 
	 * @return
	 */
	public long createWithdrawType(WithdrawType wt);
	/**
	 * 出款方式
	 * @param 
	 * @return
	 */
	public boolean updateWithdrawType(WithdrawType wt);
	/**
	 * 出款方式
	 * @param map  ID,name,status
	 * @return
	 */
	public List<WithdrawTypeDTO> searchTypes(Map<String,String> map);
	/**
	 * 出款业务
	 * @param wb
	 * @return
	 */
	public long createWithdrawBusiness(WithdrawBusiness wb);
	/**
	 * 出款业务
	 * @param 
	 * @return
	 */
	public boolean updateWithdrawBusiness(WithdrawBusiness wb);
	/**
	 * 出款业务  
	 * @param map id,name,withdrawtype,status
	 * @return
	 */
	public List<WithdrawBusinessDTO> searchBusinesses(Map<String,String> map);
	/**
	 * 出款银行
	 * @param wb
	 * @return
	 */
	public long createWithdrawBankConfig(WithdrawBankConfig wbc);
	/**
	 * 出款银行
	 * @param wbc
	 * @return
	 */
	public boolean updateWithdrawBankConfig(WithdrawBankConfig wbc);
	/**
	 * 出款银行
	 * @param map 银行API：银行名称：状态：出款方式：出款业务
	 * @return
	 */
	public List<WithdrawBankConfigDTO> searchBankConfigs(Map<String,String> map);
	
	
}
