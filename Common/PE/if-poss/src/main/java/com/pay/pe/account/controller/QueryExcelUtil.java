package com.pay.pe.account.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;




public class QueryExcelUtil {

	public static WritableWorkbook getBookNotWrite(final WritableWorkbook book, final int sheetIndex,
			final String sheetName, final String[] viewCol, final String[] classPropertity,
			final List resultList) throws WriteException, IOException {

		WritableSheet sheet = book.createSheet(sheetName, sheetIndex);
		for (int i = 0; i < viewCol.length; i++) {
			sheet.setColumnView(i, 20);
		}

		WritableCellFormat headFormat = getHeadFormat();
		for (int i = 0, len = viewCol.length; i < len; i++) {
			Label label = new Label(i, 0, viewCol[i], headFormat);
			sheet.addCell(label);
		}

		WritableCellFormat contentTextFormat = getContentTextFormat();
		WritableCellFormat contentNumberFormat = getContentNumberFormat();
		BeanWrapper sourceBw = null;
		for (int i = 0, len = resultList.size(); i < len; i++) {
			sourceBw = new BeanWrapperImpl(resultList.get(i));
			for (int j = 0, db_len = classPropertity.length; j < db_len; j++) {
				Object propertyValue = sourceBw.getPropertyValue(classPropertity[j]);
				addLabel(sheet, i, j, propertyValue, contentTextFormat, contentNumberFormat);
			}
		}

		return book;
	}

	private static void addLabel(WritableSheet sheet, int i, int j, Object propertyValue,
			WritableCellFormat contentTextFormat,WritableCellFormat contentNumberFormat) throws WriteException, RowsExceededException {
		
		if (propertyValue instanceof BigDecimal) {
			
			Number label = new Number(j, i + 1, ((BigDecimal) propertyValue).doubleValue(),
					contentNumberFormat);
			sheet.addCell(label);
		} else {
			String value = String.valueOf(propertyValue);
			Label label = new Label(j, i + 1, StringUtil.isNull(value) ? " " : value, contentTextFormat);
			sheet.addCell(label);
		}
	}

	private static WritableCellFormat getHeadFormat() throws WriteException {
		WritableFont font = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		format.setAlignment(Alignment.CENTRE);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		return format;
	}

	/**
	 * @return
	 * @throws WriteException 
	 */
	private static WritableCellFormat getContentNumberFormat() throws WriteException {
		NumberFormat nf = new NumberFormat("0.00");
		WritableCellFormat format = new WritableCellFormat(nf);
		format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		return format;
	}

	/**
	 * @return
	 * @throws WriteException
	 */
	private static WritableCellFormat getContentTextFormat() throws WriteException {
		WritableCellFormat format = new WritableCellFormat(); // font1
		format.setAlignment(Alignment.CENTRE); // 把水平对齐方式指定为居中
		format.setVerticalAlignment(VerticalAlignment.CENTRE); // 把垂直对齐方式指定为居中
		format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		return format;
	}

	public static WritableSheet resumeBroken(final WritableSheet sheet, final int begRow,
			final String[] viewCol, final String[] classPropertity, final List resultList)
			throws WriteException, IOException {
		WritableCellFormat headFormat = getHeadFormat();
		for (int i = 0, len = viewCol.length; i < len; i++) {
			Label label = new Label(i, begRow, viewCol[i], headFormat);
			sheet.addCell(label);
		}
		WritableCellFormat contentTextFormat = getContentTextFormat();
		WritableCellFormat contentNumberFormat = getContentNumberFormat();
		BeanWrapper sourceBw = null;
		for (int i = 0, len = resultList.size(); i < len; i++) {
			sourceBw = new BeanWrapperImpl(resultList.get(i));
			for (int j = 0, db_len = classPropertity.length; j < db_len; j++) {
				Object propertyValue = sourceBw.getPropertyValue(classPropertity[j]);
				if(propertyValue instanceof java.util.Date){
					
					addLabel(sheet, begRow + i, j, dateFormat.format((Date)propertyValue), contentTextFormat,contentNumberFormat);
				}else{
					addLabel(sheet, begRow + i, j, propertyValue, contentTextFormat,contentNumberFormat);	
				}
			}
		}
		return sheet;
	}

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
}
