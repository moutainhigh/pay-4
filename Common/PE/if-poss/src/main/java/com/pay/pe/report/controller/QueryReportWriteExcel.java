/**
 *  File: WriteExcel.java
 *  Description:
 *  Copyright 2010 -2010 Corporation. All rights reserved.
 *  2010-9-30     darv      Changes
 *  
 *
 */
package com.pay.pe.report.controller;

import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author sean_yi 写Excel文件
 */
public class QueryReportWriteExcel {
	
	public static Workbook createWorkbook(Map<String, Object> map,String pathAndFile){
		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = null;
		try {
			workbook = transformer.transformXLS(QueryReportWriteExcel.class.getResourceAsStream(pathAndFile),map);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
