package com.pay.rm.controller;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SimpleExcelGenerator {
	
//	private static final Logger log = Logger.getLogger(SimpleExcelGenerator.class);
	
	private static final int DEFAULT_WIDTH = 10*256;
	
	private static final String DEFAULT_SHEETNAME = "Sheet 1";

	public static Workbook generateGrid(String sheetName,String[] headers,String[] fields,List<? extends Object> valueList) throws Exception {
		Object[][] vals = new Object[valueList.size()][fields.length];
		for(int i=0;i<vals.length;i++){
			Object obj = valueList.get(i);
			for(int k=0;k<fields.length;k++){
				vals[i][k] =  getValue(obj,fields[k]);
			}
		}
		return generateGrid(sheetName,headers,vals);
	}
	
	public static Workbook generateGrid(String sheetName,String[] headers,Object [][]content) {
		return generateGrid(new HSSFWorkbook(), sheetName, headers, content);
	}
	
	public static Workbook generateGrid(Workbook workbook, String sheetName,String[] headers,Object [][]content) {
		
		if(StringUtils.isEmpty(sheetName)){
			sheetName = DEFAULT_SHEETNAME;
		}
		
//		log.info("Strat to generate simple excel["+sheetName+"]");
		
		int rowNumber = 0;
		
		
		Sheet sheet = workbook.createSheet(sheetName);
		Row headRow = sheet.createRow(rowNumber++);
		
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();   
		headerStyle.setFont(headerFont);
		
		int widths[] = new int[headers.length];
		
		for(int i=0;i<headers.length;i++){
			Cell cell = headRow.createCell(i);
			cell.setCellValue(StringUtils.isEmpty(headers[i])?"":headers[i]);
			cell.setCellStyle(headerStyle);
			widths[i] =  headers[i]==null? DEFAULT_WIDTH :headers[i].getBytes().length*256;
			
		}
		
		for(int i=0;i<content.length;i++){
			Row row = sheet.createRow(rowNumber+i);
			for(int k=0;k<headers.length;k++){
				Cell cell = row.createCell(k);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(String.valueOf(content[i][k]==null?"":content[i][k]));
				int len = content[i][k]==null? DEFAULT_WIDTH: String.valueOf(content[i][k]).getBytes().length*256;
				if(len>widths[k]){
					widths[k]= len;
				}
			}
		}
		
		for(int i=0;i<widths.length;i++){
			//sheet.setColumnWidth(i,widths[i]);
			sheet.autoSizeColumn(i);
		}
		
//		log.info("End of generating simple excel["+sheetName+"]");
		
		return workbook;
	}
	
	private static Object getValue(Object obj,String fieldName) throws Exception {
		Object result = null;
		if(obj instanceof Map<?,?>){
			Map<?,?> m = (Map<?,?>)obj;
			result = m.get(fieldName);
		}else{
			if(fieldName.indexOf(".")!=-1){
				String f1 = fieldName.substring(0, fieldName.indexOf("."));
				String f2 = fieldName.substring(fieldName.indexOf(".")+1);
				
				Field f = obj.getClass().getDeclaredField(f1);
				f.setAccessible(true);
				Object newObj = f.get(obj);
				result = getValue(newObj, f2);
			}else{
				Field f = obj.getClass().getDeclaredField(fieldName);
				f.setAccessible(true);
				result = f.get(obj);
			}
		}
		
		return result;
	}
}
