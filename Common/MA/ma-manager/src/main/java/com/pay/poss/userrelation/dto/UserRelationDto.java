package com.pay.poss.userrelation.dto;

import java.sql.Timestamp;

/**
 * 
 *  File: UesrRelation.java
 *  Description: 用户关系设置实体
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2014-5-4   liuqinghe     Create
 *
 */
public class UserRelationDto {

	/**
	 * 自增序列
	 */
	private long id;

	/**
	 * 登录名称
	 */
	private String loginId;
	
	/**
	 * 中文名
	 */
	private String name;
	
	/**
	 * 上级节点登录名称
	 */
	private String ploginId;
	
	/**
	 * 上级节点中文名
	 */
	private String pname;
	
	/**
	 * 左值
	 */
	private Integer lv;
	
	/**
	 * 右值
	 */
	private Integer rv;
	
	/**
	 * 层级
	 */
	private Integer layer;
	
	public Integer getLv() {
		return lv;
	}
	public void setLv(Integer lv) {
		this.lv = lv;
	}
	public Integer getRv() {
		return rv;
	}
	public void setRv(Integer rv) {
		this.rv = rv;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	/**
	 * 创建日期
	 */
	private Timestamp createDate;
	
	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPloginId() {
		return ploginId;
	}
	public void setPloginId(String ploginId) {
		this.ploginId = ploginId;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserRelationDto){
			UserRelationDto user = (UserRelationDto)obj;
			if(this.getId()==user.getId()){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(this.getId()).hashCode()*37;
	}
	
	
}
