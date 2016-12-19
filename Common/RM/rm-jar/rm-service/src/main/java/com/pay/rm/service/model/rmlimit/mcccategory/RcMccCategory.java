package com.pay.rm.service.model.rmlimit.mcccategory;

import com.pay.inf.model.BaseObject;

/**
 * RC_MCC_CATEGORY 行业类型
 */
public class RcMccCategory extends BaseObject {

	private static final long serialVersionUID = -2238876703537473304L;
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