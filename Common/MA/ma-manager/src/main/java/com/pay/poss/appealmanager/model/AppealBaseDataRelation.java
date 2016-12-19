package com.pay.poss.appealmanager.model;

import com.pay.inf.model.BaseObject;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBasedataRelation.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-19		gungun_zhang			Create
 */
public class AppealBaseDataRelation  extends BaseObject{
  
	private static final long serialVersionUID = 1L;
    private Integer appealRelationId;
    private String fatherDataCode;
    private String sonDataCode;
    private String relationType;
    
	public Integer getAppealRelationId() {
		return appealRelationId;
	}
	public void setAppealRelationId(Integer appealRelationId) {
		this.appealRelationId = appealRelationId;
	}
	public String getFatherDataCode() {
		return fatherDataCode;
	}
	public void setFatherDataCode(String fatherDataCode) {
		this.fatherDataCode = fatherDataCode;
	}
	public String getSonDataCode() {
		return sonDataCode;
	}
	public void setSonDataCode(String sonDataCode) {
		this.sonDataCode = sonDataCode;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

  
}