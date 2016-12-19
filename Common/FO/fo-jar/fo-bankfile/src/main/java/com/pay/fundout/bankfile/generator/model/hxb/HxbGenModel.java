/**
 *  File: HxbGenModel.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-11-21   liwei     Create
 *
 */
package com.pay.fundout.bankfile.generator.model.hxb;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**		
 *  @author lIWEI
 *  @Date 2011-11-21
 *  @Description
 */
public class HxbGenModel extends FileDetailMode {
	private static final long serialVersionUID = 4355891876720810592L;
	
	private String date;
	private String serialNo; // 记录顺序号
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
