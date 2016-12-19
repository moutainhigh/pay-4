package com.pay.poss.appealmanager.model;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBaseData.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-2		gungun_zhang			Create
 */
public class AppealBaseData  extends BaseObject{
  
	private static final long serialVersionUID = 1L;
    private Integer appealDataId;
    private String dataCode;
    private String dataName;
    private String dataType;
    
	public Integer getAppealDataId() {
		return appealDataId;
	}
	public void setAppealDataId(Integer appealDataId) {
		this.appealDataId = appealDataId;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

   
}