/**
 *  File: ExcelTestClass.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.util.excel;

import com.pay.poss.base.util.excel.ExcelUtil;

/**
 * @author zliner
 *
 */
public class ExcelTestClass {
	public static  void main(String[] args) throws Exception {
		String dbStr =  ExcelUtil.getDbStr("D:\\SVN\\write+read\\Department_Doc\\FO\\DB归档\\FO.xls",6);
		System.out.println(dbStr.toString());
	}
}
