package com.pay.poss.merchantmanager.model;

import java.util.Date;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		ProductAcctTemplate.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-3		gungun_zhang			Create
 */

public class ProductAcctTemplate {
	
	private Long id;
    private Long productId;
    private Long memberAcctTemplateId;
    private Integer scene;
    private String featureIds;
    private Integer productKey;
    private Date creationDate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getMemberAcctTemplateId() {
		return memberAcctTemplateId;
	}
	public void setMemberAcctTemplateId(Long memberAcctTemplateId) {
		this.memberAcctTemplateId = memberAcctTemplateId;
	}
	public Integer getScene() {
		return scene;
	}
	public void setScene(Integer scene) {
		this.scene = scene;
	}
	public String getFeatureIds() {
		return featureIds;
	}
	public void setFeatureIds(String featureIds) {
		this.featureIds = featureIds;
	}
	
	public Integer getProductKey() {
		return productKey;
	}
	public void setProductKey(Integer productKey) {
		this.productKey = productKey;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

    
    
}