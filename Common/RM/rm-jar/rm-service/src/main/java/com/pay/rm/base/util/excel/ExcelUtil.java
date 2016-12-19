/**
 *  File: ExcelUtil.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.rm.base.util.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * excel解析工具类
 * @author zliner
 *
 */
public final class ExcelUtil {

    /**
     * Make it private to prevent it from being instantiating
     *
     */
    private ExcelUtil() {
    }

    /**
     * Converts an Excel file to <code>Book</code> Java object
     * @param fileName The full file name with path info
     * @return A book object
     * @throws IOException if file not found or fail to read file
     */
    public static Book toBook(String fileName) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
        HSSFWorkbook hssfWorkBook = new HSSFWorkbook(fs);
        Book book = getBook(hssfWorkBook);
        book.setBookName(fileName);
        return book;
    }

    /**
     * Converts a <code>Book</code> object to an Excel file and writes to filesystem
     * @param book The book object
     * @param fileName The output file name with path info
     * @throws IOException if fail to write file
     */
    public static void toFile(Book book, String fileName) throws IOException {
        HSSFWorkbook hssfWorkbook = createWorkBook(book);
        FileOutputStream fos = new FileOutputStream(fileName);
        hssfWorkbook.write(fos);
        fos.close();
    }

    private static Book getBook(HSSFWorkbook hssfWorkbook) {
        List sheets = new ArrayList();
        Sheet sheet = null;

        for (int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++) {
            sheet = new Sheet();
            sheet.setSheetName(hssfWorkbook.getSheetName(i));
            sheet.setRows(getRows(hssfWorkbook.getSheetAt(i)));
            sheets.add(sheet);
        }
        Book book = new Book();
        book.setSheets(sheets);
        return book;
    }

    private static List getRows(HSSFSheet hssfSheet) {
        List rows = new ArrayList();
        Row row = null;

        for (int i = 0; i < hssfSheet.getLastRowNum() + 1; i++) {
            row = new Row();
            row.setCells(getCells(hssfSheet.getRow(i)));
            rows.add(row);
        }
        return rows;
    }

    private static List getCells(HSSFRow hssfRow) {
        List cells = new ArrayList();
        Cell cell = null;
        if (hssfRow == null) {
            return cells;
        }

        for (short i = 0; i < hssfRow.getLastCellNum(); i++) {
            HSSFCell hssfCell = hssfRow.getCell(i);
            cell = new Cell(getStringValue(hssfCell));
            cells.add(cell);
        }
        return cells;
    }

    private static String getStringValue(HSSFCell hssfCell) {
        if (hssfCell == null) {
            return "";
        }
        if (HSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()) {
            return hssfCell.getStringCellValue();
        }

        if (HSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
            return String.valueOf((int) hssfCell.getNumericCellValue());
        }

        if (HSSFCell.CELL_TYPE_BOOLEAN == hssfCell.getCellType()) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        }

        if (HSSFCell.CELL_TYPE_BLANK == hssfCell.getCellType()) {
            return hssfCell.getStringCellValue();
        }

        return "";
    }

    private static HSSFWorkbook createWorkBook(Book book) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = null;

        List sheets = book.getSheets();
        Sheet sheet = null;

        for (Iterator it = sheets.iterator(); it.hasNext();) {
            sheet = (Sheet) it.next();
            hssfSheet = hssfWorkbook.createSheet(sheet.getSheetName());
            createRows(hssfSheet, sheet.getRows());
        }
        return hssfWorkbook;
    }

    private static void createRows(HSSFSheet hssfSheet, List rows) {
        HSSFRow hssfRow = null;
        Row row = null;
        int i = 0;

        for (Iterator it = rows.iterator(); it.hasNext(); i++) {
            row = (Row) it.next();
            hssfRow = hssfSheet.createRow(i);
            createCells(hssfRow, row.getCells());
        }
    }

    private static void createCells(HSSFRow hssfRow, List cells) {
        HSSFCell hssfCell = null;
        Cell cell = null;
        short i = 0;

        for (Iterator it = cells.iterator(); it.hasNext(); i++) {
            cell = (Cell) it.next();
            hssfCell = hssfRow.createCell(i);
            hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
            hssfCell.setCellValue(cell.getValue());
        }
    }
    public static String getDbStr(String filePath,int sheetVal) throws Exception {
    	Book book = ExcelUtil.toBook(filePath);
		Sheet nameSheet = book.getSheet(sheetVal);
		List<Row> rowList =  nameSheet.getRows();
		Row rowTable = nameSheet.getRow(1);
		Row rowDesc= nameSheet.getRow(2);
		String tableName = rowTable.getCell(1).getValue();
		String pkName = rowTable.getCell(4).getValue();
		String tableDesc = rowDesc.getCell(2).getValue();
		Map<String,TableParam> mapVal = new HashMap<String, TableParam>();
		List<Row> rowListSmth = new ArrayList<Row>();
		for(int i = 0 ;i < rowList.size() ;i ++) {
			if (i <= 3) {
				continue;
			}else {
				rowListSmth.add(rowList.get(i));
			}
		}
		for(Row row : rowListSmth) {
			List<Cell> cellList = row.getCells();
			TableParam param = new TableParam();
			for(int i = 0 ; i < 7; i ++) {
				if(i == 0) {
				}else if(i == 2) {
					param.setColumnDesc(cellList.get(i).getValue());
				}else if(i == 1) {
					param.setColumn(cellList.get(i).getValue());
				}else if(i == 3) {
					param.setColumnType(cellList.get(i).getValue());
				}else if(i == 4) {
					param.setColumnLen(null != cellList.get(i).getValue() ? cellList.get(i).getValue() : "");
				}else if(i == 5) {
					param.setIsNull(null != cellList.get(i).getValue() ? cellList.get(i).getValue() : "");
				}else if(i == 6) {
					param.setDefaultValue(null != cellList.get(i).getValue() ? cellList.get(i).getValue() : "");
				}else {
				}
				if(i == 6) {
					mapVal.put(param.getColumn(), param);
				}
			}
		}		StringBuffer tableStrVal = new StringBuffer("create table ").append(tableName).append("\n");
		tableStrVal.append("(");
		Set<String> keySet = mapVal.keySet();
		Iterator<String> keyValIteror = keySet.iterator();
		Iterator<String> keyValIteror1= keySet.iterator();
		while(keyValIteror.hasNext()) {
			String key = keyValIteror.next();
			TableParam param = mapVal.get(key);
			tableStrVal.append(key).append("  ");
			if("".equals(param.getColumnLen())) {
				tableStrVal.append(param.getColumnType()).append("  ");
			}else { 
				tableStrVal.append(" ").append(param.getColumnType()).append("(").append(param.getColumnLen())
				.append(")").append("  ");
			}
			if(!"".equals(param.getDefaultValue())) {
				tableStrVal.append(" default ").append(param.getDefaultValue()).append("  ");
			}
			if("".equals(param.getIsNull())) {
				tableStrVal.append(" not null");
			}
			if(keyValIteror.hasNext()) {
				tableStrVal.append(",").append("\n");
			}else {
				tableStrVal.append(" );").append("\n");
			}
		}
		tableStrVal.append("comment on table ").append(tableName).append(" is '").append(tableDesc).append(" '; \n");
		while(keyValIteror1.hasNext()) {
			String key = keyValIteror1.next();
			TableParam param = mapVal.get(key);
			tableStrVal.append("comment on column ").append(tableName).append(".").append(key)
			.append(" is '").append(param.getColumnDesc()).append("';").append("\n");
		}
		tableStrVal.append(" alter table ").append(tableName).append(" ")
		.append("add constraint ").append(tableName).append("_PK primary key (").append(pkName).append(")").append("\n")
		.append(" using index tablespace USERS pctfree 10 initrans 2 maxtrans 255 storage (  initial 64K  next 1M  minextents 1   maxextents unlimited);");
		return tableStrVal.toString();
    }
	
}

