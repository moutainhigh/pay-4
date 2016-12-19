package com.pay.base.dto;

import com.pay.inf.dto.MutableDto;

public class FeatureMenuDto implements MutableDto {
	
	private Long featureMenuId;
	private Long featureId;
	private Long menuId;

	public Long getFeatureMenuId() {
		return featureMenuId;
	}

	public void setFeatureMenuId(Long featureMenuId) {
		this.featureMenuId = featureMenuId;
	}

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
