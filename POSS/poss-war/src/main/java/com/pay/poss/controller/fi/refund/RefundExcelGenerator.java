package com.pay.poss.controller.fi.refund;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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

public class RefundExcelGenerator {
	
	private static final int DEFAULT_WIDTH = 10*256;
	
	private static final String DEFAULT_SHEETNAME = "Sheet 1";

	public static Workbook generateGridByMap(String sheetName,String[] headers,String[] fields,
			List<Map> dataList,String enddate,String datesb) throws Exception {
		Object[][] vals = null;

		List<String> headerList = new ArrayList<String>();
		List<String> datelist = new ArrayList<String>();
		DecimalFormat df=new DecimalFormat("0.00%");
		
		Collections.addAll(headerList, headers);
		String[] dateHeaders =new String[headers.length];
		dateHeaders[0] = "日期";
		dateHeaders[4] = datesb;
		
		Collections.addAll(datelist, dateHeaders);
		vals = new Object[dataList.size()][fields.length];
		for(int i=0; i < dataList.size(); i++){
			Object obj = dataList.get(i);
			for(int k=0; k < fields.length; k++){
				vals[i][k] = getValue(obj,fields[k]);
			}
		}
		
		return generateGrid(sheetName,headerList,vals,datelist);
	}
	public static String[] concat(String[] a, String[] b) {  
		   String[] c= new String[a.length+b.length];  
		   System.arraycopy(a, 0, c, 0, a.length);  
		   System.arraycopy(b, 0, c, a.length, b.length);  
		   return c;  
	}
	public static Workbook generateGrid(String sheetName,List<String> headers,Object [][]content,List<String> datelist) {
		return generateGrid(new HSSFWorkbook(), sheetName, headers, content,datelist);
	}
	
	public static Workbook generateGrid(Workbook workbook, String sheetName,List<String> headers,Object [][]content,List<String> datelist) {
		
		if(StringUtils.isEmpty(sheetName)){
			sheetName = DEFAULT_SHEETNAME;
		}
		
		int rowNumber = 1;
		
		Sheet sheet = workbook.createSheet(sheetName);
		Row headRow = sheet.createRow(rowNumber++);
		Row dateheadRow = sheet.createRow(0);
		
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();   
		headerStyle.setFont(headerFont);
		
		int widths[] = new int[headers.size()];
		
		for(int i=0;i<datelist.size();i++){
			Cell cell = dateheadRow.createCell(i);
			cell.setCellValue(datelist.get(i)==null?"":datelist.get(i).toString());
			cell.setCellStyle(headerStyle);
			widths[i] =  datelist.get(i)==null? DEFAULT_WIDTH :datelist.get(i).toString().getBytes().length*256;
			
		}
		
		for(int i=0;i<headers.size();i++){
			Cell cell = headRow.createCell(i);
			cell.setCellValue(StringUtils.isEmpty(headers.get(i))?"":headers.get(i));
			cell.setCellStyle(headerStyle);
			widths[i] =  headers.get(i)==null? DEFAULT_WIDTH :headers.get(i).getBytes().length*256;
			
		}
		
		for(int i=0;i<content.length;i++){
			Row row = sheet.createRow(rowNumber+i);
			for(int k=0;k<headers.size();k++){
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
