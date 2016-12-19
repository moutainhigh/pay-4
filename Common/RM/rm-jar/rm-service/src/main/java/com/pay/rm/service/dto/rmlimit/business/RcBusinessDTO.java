package com.pay.rm.service.dto.rmlimit.business;

import com.pay.inf.model.BaseObject;

/**
 * RC_BUSINESS 业务类型
 */
public class RcBusinessDTO extends BaseObject {

	private static final long serialVersionUID = 8220920069755265221L;

	private String businessName;
	private Integer status;
	private Integer businessId;
	private Integer businessType;

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

}