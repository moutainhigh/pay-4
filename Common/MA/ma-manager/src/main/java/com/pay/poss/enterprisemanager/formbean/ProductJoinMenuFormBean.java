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
 
 
public class ProductJoinMenuFormBean {
	private String productId;
	private String productName;
	private String allowObject;
	private String[] funcno;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String[] getFuncno() {
		return funcno;
	}
	public void setFuncno(String[] funcno) {
		this.funcno = funcno;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAllowObject() {
		return allowObject;
	}
	public void setAllowObject(String allowObject) {
		this.allowObject = allowObject;
	}
    
	
}
