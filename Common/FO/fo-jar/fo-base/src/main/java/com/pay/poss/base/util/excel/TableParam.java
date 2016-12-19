/**
 *  File: TableParam.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.util.excel;

/**
 * @author zliner
 *
 */
public class TableParam {
	private String column;
	private String columnDesc;
	private String columnType;
	private String columnLen;
	private String isNull;
	private String defaultValue;
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getColumnDesc() {
		return columnDesc;
	}
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnLen() {
		return columnLen;
	}
	public void setColumnLen(String columnLen) {
		this.columnLen = columnLen;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
