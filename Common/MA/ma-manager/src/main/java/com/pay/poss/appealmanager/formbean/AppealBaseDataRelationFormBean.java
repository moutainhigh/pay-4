package com.pay.poss.appealmanager.formbean;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBaseDataRelationAddFormBean.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-3		gungun_zhang			Create
 */

public class AppealBaseDataRelationFormBean {
    private String appealRelationId;
    private String fatherDataCode;
    private String sonDataCode;
    private String relationType;
    
	public String getAppealRelationId() {
		return appealRelationId;
	}
	public void setAppealRelationId(String appealRelationId) {
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