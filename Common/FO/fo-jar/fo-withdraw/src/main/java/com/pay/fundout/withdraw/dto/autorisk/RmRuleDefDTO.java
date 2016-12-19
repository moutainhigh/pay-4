package com.pay.fundout.withdraw.dto.autorisk;

/**
 * 风控规则配置
 * 
 * @author Administrator
 * 
 */
public class RmRuleDefDTO {

	String ruleCode; // 规则代码
	String ruleValue; // 规则值
	String ruleDesc; // 规则描述

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

}
