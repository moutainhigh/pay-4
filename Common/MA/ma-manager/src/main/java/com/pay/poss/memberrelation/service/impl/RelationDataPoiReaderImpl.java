package com.pay.poss.memberrelation.service.impl;

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

import com.pay.poss.memberrelation.service.RelationDataReader;



/**
 * 关联数据读取器Poi实现
 */
public class RelationDataPoiReaderImpl implements RelationDataReader {
	
	
	private static final Log LOG = LogFactory.getLog(RelationDataPoiReaderImpl.class);
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
	 * 门店编号
	 */
	private static final int RELATION_STORE_NUMBERS_INDEX_IN_ROW = 0;
	
	/**
	 * 门店地址
	 */
	private static final int RELATION_STORE_ADDRESS_INDEX_IN_ROW = 1;
	
	/**
	 * 店主姓名
	 */
	private static final int RELATION_STORE_NAME_INDEX_IN_ROW = 2;
	
	/**
	 * 手机号码
	 */
	private static final int RELATION_MOBILE_NO_INDEX_IN_ROW = 3;
	
	/**
	 * 账户（Email）
	 */
	private static final int AMOUNT_EMAIL_INDEX_IN_ROW = 4;
	
	/**
	 * 读明细起始序号
	 */
	private static final int DETAIL_START_INDEX = 2;
	
	
	private static final int FATHER_MEMBER_CODE_INDEX=1;
	private static final int FATHER_MEMBER_CODE_ROW=1;

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
	
	
	public RelationDataPoiReaderImpl(InputStream inputStream) {
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

	@Override
	public String getRelationStoreNumbers() {
		checkState();
		return rows.get(currentIndex)
			.getCell(RELATION_STORE_NUMBERS_INDEX_IN_ROW).getValue();
	}

	@Override
	public String getRelationStoreAddress() {
		checkState();
		return rows.get(currentIndex)
			.getCell(RELATION_STORE_ADDRESS_INDEX_IN_ROW).getValue();
	}

	@Override
	public String getRelationStoreName() {
		checkState();
		return rows.get(currentIndex)
			.getCell(RELATION_STORE_NAME_INDEX_IN_ROW).getValue();
	}

	@Override
	public String getRelationMobileNo() {
		checkState();
		return rows.get(currentIndex)
			.getCell(RELATION_MOBILE_NO_INDEX_IN_ROW).getValue();
	}

	@Override
	public String getAmountEmail() {
		checkState();
		return rows.get(currentIndex)
			.getCell(AMOUNT_EMAIL_INDEX_IN_ROW).getValue();
	}
	
	@Override
	public String getFatherMemberCode() {
		checkState();
		return rows.get(FATHER_MEMBER_CODE_INDEX)
			.getCell(FATHER_MEMBER_CODE_ROW).getValue();
	}
	
	/**
	 * 从流读入数据
	 */
	public void read() throws IOException {
		LOG.info("Start");
		
		HSSFWorkbook wb = new HSSFWorkbook(inputStream);
		
		HSSFSheet sheet = wb.getSheetAt(SHEET_INDEX);
		
		rows = new ArrayList<Row>();
		for (int i = 0, rowNum = sheet.getLastRowNum(); i <= rowNum; i++) {
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
		endIndex = rows.size()-1 ;
		
		LOG.info("currentIndex size : " + currentIndex+  "   endIndex size : " + endIndex);
		
		LOG.info("End");
	}
	
	public static void main(String[] argv) throws IOException {
		String file = "c:\\template.xls";
		InputStream in = new FileInputStream(file);
		
		RelationDataPoiReaderImpl reader = new RelationDataPoiReaderImpl(in);
		reader.read();
		
		while (reader.next()) {
			System.out.println("****************");
			System.out.print(reader.getAmountEmail() + "|");
			System.out.print(reader.getFatherMemberCode() + "|");
			System.out.print(reader.getRelationMobileNo() + "|");
			System.out.print(reader.getRelationStoreAddress() + "|");
			System.out.println(reader.getRelationStoreName() + "|");
			System.out.println(reader.getRelationStoreNumbers() + "|");

		}
		
		reader.close();
	}





}
