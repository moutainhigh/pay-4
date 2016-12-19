/**
 *  File: PositionDto.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-6   Sany        Create
 *
 */
package com.pay.fo.elecbills;

/**
 * 回单坐标DTO
 */
public class PositionDto {
	
	/** 回单属性名 **/
	private String fieldName;
	
	/** 属性X相对坐标 **/
	private int x;
	
	/** 属性Y相对坐标 **/
	private int y;

	public String getFieldName() {
		return fieldName;
	}

	public void setFiledName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
