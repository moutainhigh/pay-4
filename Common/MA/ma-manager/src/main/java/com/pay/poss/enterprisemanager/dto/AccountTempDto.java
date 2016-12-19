package com.pay.poss.enterprisemanager.dto;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-14		gungun_zhang			Create
 */



public class AccountTempDto {


	private String accountTempId;
	private String accountTempName;
	private String pageEndRow;
	private String pageStartRow;
	
	public String getAccountTempId() {
		return accountTempId;
	}
	public void setAccountTempId(String accountTempId) {
		this.accountTempId = accountTempId;
	}
	public String getAccountTempName() {
		return accountTempName;
	}
	public void setAccountTempName(String accountTempName) {
		this.accountTempName = accountTempName;
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