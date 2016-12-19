package com.pay.poss.merchantmanager.model;

import java.util.Date;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		SignMessage.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-29		tianqing_wang			Create
 */

public class SignMessage {
	
    private Long signMessageId;//主键
    private String departmentName;
    private String searchKey;
    private String departmentPrincipal;
    private String email;
    private String principalMobile;
    
    private String value1;
    private String value2;
    
    private Date gmtCreate;
    private Date gmtModified;
    
    private String creator;
    private String modifier;
    private String isDeleted;
	public Long getSignMessageId() {
		return signMessageId;
	}
	public void setSignMessageId(Long signMessageId) {
		this.signMessageId = signMessageId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getDepartmentPrincipal() {
		return departmentPrincipal;
	}
	public void setDepartmentPrincipal(String departmentPrincipal) {
		this.departmentPrincipal = departmentPrincipal;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPrincipalMobile() {
		return principalMobile;
	}
	public void setPrincipalMobile(String principalMobile) {
		this.principalMobile = principalMobile;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
   
    

    
    
}