package com.pay.pe.manualbooking.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.pe.manualbooking.dao.VouchDataReader;



/**
 * 手工记账数据读取器Poi实现
 */
public class VouchDataPoiReaderImpl implements VouchDataReader {
	
	
	private static final Log LOG = LogFactory.getLog(VouchDataPoiReaderImpl.class);
	class Row {
		private List<Cell> cells;
		
	    public Row(List<Cell> cells) {
	        this.cells = cells;
	    }

	    public Cell getCell(int cellNo) {
	        if (cellNo < 0 || cellNo >= cells.size()) {
	            throw new IllegalArgumentException();
	        }
	        return cells.get(cellNo);
	    }
	}
	
	class Cell {
		private String value;

	    public Cell(String value) {
	        this.value = value;
	    }
	    
	    public String getValue() {
	        return value;
	    }
	}
	
	/**
	 * 页序号
	 */
	private static final int SHEET_INDEX = 0;
	
	/**
	 * 备注字段序号
	 */
	private static final int REMARK_INDEX_IN_ROW = 0;
	
	/**
	 * 账号字段序号
	 */
	private static final int ACCOUNTCODE_INDEX_IN_ROW = 1;
	
	/**
	 * 账号名称字段序号
	 */
	private static final int ACCOUNTNAME_INDEX_IN_ROW = 2;
	
	/**
	 * 借款额序号
	 */
	private static final int CR_AMOUNT_INDEX_IN_ROW = 3;
	
	/**
	 * 贷款额序号
	 */
	private static final int DR_AMOUNT_INDEX_IN_ROW = 4;
	
	/**
	 * 读明细起始序号
	 */
	private static final int DETAIL_START_INDEX = 2;
	
	/**
	 * 读取流
	 */
	private InputStream inputStream;
	
	/**
	 * 明细列表
	 */
	private List<Row> rows;
	
	/**
	 * 当前游标
	 */
	private int currentIndex;
	
	/**
	 * 结束序号
	 */
	private int endIndex;
	
	
	public VouchDataPoiReaderImpl(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void close() throws IOException {
		LOG.info("Start");
		if (inputStream != null) {
			inputStream.close();
		}
		LOG.info("End");
	}

	private void checkState() {
		if (currentIndex <= DETAIL_START_INDEX || currentIndex > endIndex) {
			throw new IllegalStateException("invalid state in VouchDataPoiReader");
		}
	}
	
	public String getAccountCode() {
		checkState();
		return rows.get(currentIndex)
			.getCell(ACCOUNTCODE_INDEX_IN_ROW).getValue();
	}

	public String getAccountName() {
		checkState();
		return rows.get(currentIndex)
			.getCell(ACCOUNTNAME_INDEX_IN_ROW).getValue();
	}

	public String getCrAmount() {
		checkState();
		return rows.get(currentIndex)
			.getCell(CR_AMOUNT_INDEX_IN_ROW).getValue();
	}

	public String getDrAmount() {
		checkState();
		return rows.get(currentIndex)
			.getCell(DR_AMOUNT_INDEX_IN_ROW).getValue();
	}

	public String getVouchDetailRemark() {
		checkState();
		return rows.get(currentIndex)
			.getCell(REMARK_INDEX_IN_ROW).getValue();
	}

	public boolean isFirst() {
		return (currentIndex == (DETAIL_START_INDEX + 1)) ? true : false;
	}

	public boolean isLast() {
		return (currentIndex == endIndex) ? true : false;
	}

	public boolean next() {
		if (currentIndex >= endIndex) {
			return false;
		}
		currentIndex++;
		return true;
		
	}

	/**
	 * 从流读入数据
	 */
	public void read() throws IOException {
		LOG.info("read Excel Start");
		
		HSSFWorkbook wb = new HSSFWorkbook(inputStream);
		
		HSSFSheet sheet = wb.getSheetAt(SHEET_INDEX);
		
		rows = new ArrayList<Row>();
		for (int i = 0, rowNum = sheet.getLastRowNum(); i < rowNum; i++) {
			HSSFRow hssfRow = sheet.getRow(i);
			
			List<Cell> cells = new ArrayList<Cell>();
			for (int j = 0, colNum = hssfRow.getLastCellNum(); j < colNum; j++) {
				//hssfRow.getCell((short)j).setCellType(HSSFCell.CELL_TYPE_STRING);
				
				
				HSSFCell hssfCell = hssfRow.getCell((short)j) ;
				
				//String value = hssfRow.getCell((short)j).getStringCellValue();
				String value = "";
				if(null!=hssfCell){
				int cellType = hssfCell.getCellType();				
				if (cellType == HSSFCell.CELL_TYPE_STRING) {
					value = hssfCell.getStringCellValue();
				} else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
					double d = hssfCell.getNumericCellValue();
					BigDecimal b = null;
					if (j == 1) {
						b = new BigDecimal(d);
					} else {
						b = new BigDecimal(d + "");
					}
					if (!BigDecimal.ZERO.equals(b)) {
						value = b + "";
					} 
				}
				}
				Cell cell = new Cell(value);
				cells.add(cell);
			}
			
			Row row = new Row(cells);
			rows.add(row);
		}
		
		currentIndex = DETAIL_START_INDEX;
		endIndex = rows.size() - 2;
		
		LOG.info("Row size : " + rows.size());
		
		LOG.info("read Exce  End");
	}
	
	public static void main(String[] argv) throws IOException {
		String file = "/myprog/template.xls";
		InputStream in = new FileInputStream(file);
		
		VouchDataPoiReaderImpl reader = new VouchDataPoiReaderImpl(in);
		reader.read();
		
		while (reader.next()) {
			System.out.println("****************");
			System.out.print(reader.getAccountCode() + "|");
			System.out.print(reader.getAccountName() + "|");
			System.out.print(reader.getVouchDetailRemark() + "|");
			System.out.print("CR :"+reader.getCrAmount() + "|");
			System.out.println("DR :"+reader.getDrAmount() + "|");
		}
		System.out.println();
		reader.close();
	}

}
