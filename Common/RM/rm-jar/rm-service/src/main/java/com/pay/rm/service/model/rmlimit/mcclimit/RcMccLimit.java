package com.pay.rm.service.model.rmlimit.mcclimit;

import com.pay.inf.model.BaseObject;

/**
 * RC_MCC_LIMIT 行业限额
 */
public class RcMccLimit extends BaseObject {

	private static final long serialVersionUID = 9025893841438494954L;
	private Long mcc;
	private Long singleLimit;
	private Integer status;
	private String categoryId;
	private String categoryDesc;
	private String mccDesc;

	public Long getMcc() {
		return mcc;
	}

	public void setMcc(Long mcc) {
		this.mcc = mcc;
	}

	public Long getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(Long singleLimit) {
		this.singleLimit = singleLimit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getMccDesc() {
		return mccDesc;
	}

	public void setMccDesc(String mccDesc) {
		this.mccDesc = mccDesc;
	}

}