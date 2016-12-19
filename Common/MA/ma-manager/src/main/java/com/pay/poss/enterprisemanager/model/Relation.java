package com.pay.poss.enterprisemanager.model;

import com.pay.inf.model.BaseObject;

public class Relation extends BaseObject{

	private static final long serialVersionUID = 1L;
	private String fatherCode;
	private String code;
	private String name;
	
	public String getFatherCode() {
		return fatherCode;
	}
	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
