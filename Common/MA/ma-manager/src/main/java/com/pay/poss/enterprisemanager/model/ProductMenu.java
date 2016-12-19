package com.pay.poss.enterprisemanager.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;
/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductMenu.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-11		gungun_zhang			Create
 */

public class ProductMenu extends BaseObject{
	 
	private static final long serialVersionUID = 1L;
	private Integer productMenuId;
    private Integer productId;
    private Integer menuId;
    private Date createDate;
    private Date updateDate;
	public Integer getProductMenuId() {
		return productMenuId;
	}
	public void setProductMenuId(Integer productMenuId) {
		this.productMenuId = productMenuId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}