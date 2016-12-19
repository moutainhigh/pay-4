package com.pay.poss.base.model;

public class BatchRule {
	private Long ruleKy;
	private String ruleCode;
	private String ruleName;
	private String batchNumGenetor;

	public Long getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(Long ruleKy) {
		this.ruleKy = ruleKy;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getBatchNumGenetor() {
		return batchNumGenetor;
	}

	public void setBatchNumGenetor(String batchNumGenetor) {
		this.batchNumGenetor = batchNumGenetor;
	}

}
