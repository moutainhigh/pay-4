package com.pay.poss.merchantmanager.dto;

import java.util.Date;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		SignMessageDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-29		tianqing_wang			Create
 */

public class SignMessageDto {
	
    private Long signMessageId;           //主键
    private String departmentName;		  //部门 (频道)名称
    private String searchKey;			  //搜索字
    private String departmentPrincipal;   //部门 (频道)负责人
    private String email;				  //部门 (频道)email
    private String principalMobile;		  //部门 (频道)负责人电话
    
    private String value1;
    private String value2;
    
    private Date gmtCreate;
    private Date gmtModified;
    private String strGmtCreate;
	private String strgmtModified;
    
    private String creator;
    private String modifier;
    private String isDeleted;

	private String startDate; //创建日期start
	private String endDate;   //创建日期end
	
	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行
	private String updateFlag;  //修改频道名称时查询数据的设值判断
	
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
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
		if(null != departmentPrincipal && !"".equals(departmentPrincipal)){
			String str = departmentPrincipal.trim();
			return str;
		}else{
			return departmentPrincipal;
		}
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
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
    
	public String getStrGmtCreate() {
		return strGmtCreate;
	}
	public void setStrGmtCreate(String strGmtCreate) {
		this.strGmtCreate = strGmtCreate;
	}
	public String getStrgmtModified() {
		return strgmtModified;
	}
	public void setStrgmtModified(String strgmtModified) {
		this.strgmtModified = strgmtModified;
	}
    
    
}