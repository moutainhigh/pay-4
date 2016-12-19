/**
 * 
 */
package com.pay.poss.featuremenu.dto;

/**
 * @Description 用于页面返回json对象
 * @project 	ma-manager
 * @file 		JsonDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-7-6			DDR				Create
 */
public class JSONDto {
	private String result;
	private String msg;
	private String id;
	
	public JSONDto(String result, String msg, String id) {
		super();
		this.result = result;
		this.msg = msg;
		this.id = id;
	}
	
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
