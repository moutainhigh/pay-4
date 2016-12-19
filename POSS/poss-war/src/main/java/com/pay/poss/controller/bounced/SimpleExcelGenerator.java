package com.pay.poss.controller.bounced;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pay.poss.controller.fi.dto.BouncedFraudResultDTO;

public class SimpleExcelGenerator {
	
//	private static final Logger log = Logger.getLogger(SimpleExcelGenerator.class);
	
	private static final int DEFAULT_WIDTH = 10*256;
	
	private static final String DEFAULT_SHEETNAME = "Sheet 1";

	public static Workbook generateGridByMap(String sheetName,String[] headers,String[] fields,
			Map<String,List<BouncedFraudResultDTO>> valueMap,String enddate,String datesb) throws Exception {
		Object[][] vals = null;
		int	m=0;
		int size=0;

		List<String> headerList = new ArrayList<String>();
		List<String> datelist = new ArrayList<String>();
		DecimalFormat df=new DecimalFormat("0.00%");
		for (Entry<String,List<BouncedFraudResultDTO>> entry :valueMap.entrySet()) {
			List<BouncedFraudResultDTO> list=entry.getValue();
			if (!CollectionUtils.isEmpty(list)) {
				size = entry.getValue().size();
				for (BouncedFraudResultDTO dto : entry.getValue()) {
					if (dto.getBouncedRate() != null) {
						dto.setSbouncedRate(df.format(dto.getBouncedRate()));
					}
					if (dto.getFraudRate() != null) {
						dto.setSfraudRate(df.format(dto.getFraudRate()));
					}
				}
			} else {
				continue;
			}
			Collections.addAll(headerList, headers);
			String[] dateHeaders =new String[headers.length];
			dateHeaders[0]="日期";
			if(enddate.equals(entry.getKey()))
			{
				dateHeaders[4]=datesb;
			}else{
			dateHeaders[4]=entry.getKey();
			}
			Collections.addAll(datelist, dateHeaders);
		}
		vals = new Object[size][fields.length*valueMap.size()];
		for (Entry<String,List<BouncedFraudResultDTO>> entry :valueMap.entrySet()) {
		List<? extends Object> valueList=entry.getValue();
		
		for(int i=0;i<vals.length;i++){
			Object obj = valueList.get(i);
			for(int k=0;k<fields.length;k++){
				vals[i][fields.length*m+k] =  getValue(obj,fields[k]);
			}
		}
		m++;
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
		
//		log.info("Strat to generate simple excel["+sheetName+"]");
		
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
