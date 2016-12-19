package com.pay.rm.service.service.rmrefund;

import java.util.List;
import java.util.Map;

import com.pay.rm.service.dto.rmrefund.RefundRuleDTO;
import com.pay.rm.service.model.rmrefund.RefundRule;
import com.pay.rm.service.service.RmBaseService;

/**
 * 充退规则
 * @Description 
 * @project 	rm-service
 * @file 		RefundRuleService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-11-1		Volcano.Wu			Create
 */
public interface RefundRuleService extends RmBaseService<RefundRule,RefundRuleDTO>{
	/**
	 * 规则列表
	 * @return
	 */
	//public List<RefundRule> findAllRules();
	/**
	 * 规则修改
	 * @param cbr
	 * @return
	 */
	//public boolean updateCBRule(RefundRule cbr);
	//public boolean deleteCBRule(long id);


	//public long createCBRule(RefundRule cbr);
	
	/**
	 * 获得一个规则
	 * @return
	 */
	public RefundRule getRule();
	
//	public boolean deleteCBRule(int id);
	/**
	 * 规则匹配
	 * list 充值流水列表
	 * Map里面格式定义
	 * key:bamount 充退金额列表
	 * key:amount 可充退金额列表
	 * key:date 充值日期列表
	 * total 可用余额
	 * chargeBackTotal 充退总额
	 * userid 用户ID
	 * @return boolean
	 */
	public Map<String,String> ruleFilter(long userid,long total,long chargeBackTotal,List<Map<String,Object>> list) ;
	/**
	 * 分页查询
	 * @param wl
	 * @return
	 */
	//public Page<ChargeBackRuleFormBean> search(Page<ChargeBackRuleFormBean> page,ChargeBackRuleDTO chargeBackRuleDTO) throws Exception;
	
}
