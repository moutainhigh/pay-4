package com.pay.rm.service.model.rmlimit.risklevel;

import com.pay.inf.model.BaseObject;

/**
 * RC_RISK_LEVEL 风险等级
 */

public class RcRiskLevel extends BaseObject {

	private static final long serialVersionUID = -6242772255729512291L;
	private Integer riskLevel;
	private Integer status;
	private String levelName;

	public Integer getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(Integer riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}