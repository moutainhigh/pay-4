package com.pay.rm.service.dto.rmlimit.risklevel;

import com.pay.inf.model.BaseObject;

/**
 * RC_RISK_LEVEL 风险等级
 */

public class RcRiskLevelDTO extends BaseObject {

	private static final long serialVersionUID = -5980351059222111821L;
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