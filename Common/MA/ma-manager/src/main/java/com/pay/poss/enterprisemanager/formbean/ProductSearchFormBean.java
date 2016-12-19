package com.pay.poss.enterprisemanager.formbean;



/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductSearchFormBean.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-11		gungun_zhang			Create
 */
 
 
public class ProductSearchFormBean {
	private String productId;
    private String productName;
    private String description;
    private String allowObject;
    private String isDefault;
    private Integer productType;
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAllowObject() {
		return allowObject;
	}
	public void setAllowObject(String allowObject) {
		this.allowObject = allowObject;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
	
    
	
	    
}
