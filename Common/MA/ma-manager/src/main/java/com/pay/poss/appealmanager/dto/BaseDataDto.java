package com.pay.poss.appealmanager.dto;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		BasedataDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-20		gungun_zhang			Create
 */

public class BaseDataDto {
	
	private String id;
	private String code;
	private String name;
	private String type;
	
	private String pageEndRow;
	private String pageStartRow;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(String pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public String getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(String pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	
	

}
