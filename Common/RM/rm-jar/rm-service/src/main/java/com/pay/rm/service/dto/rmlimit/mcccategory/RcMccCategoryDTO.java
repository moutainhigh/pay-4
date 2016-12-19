package com.pay.rm.service.dto.rmlimit.mcccategory;

import com.pay.inf.model.BaseObject;

/**
 * RC_MCC_CATEGORY 行业类别
 */
public class RcMccCategoryDTO extends BaseObject {

	private static final long serialVersionUID = 5004619968158025378L;
	private String categoryName;
	private Integer status;
	private String categoryId;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

}