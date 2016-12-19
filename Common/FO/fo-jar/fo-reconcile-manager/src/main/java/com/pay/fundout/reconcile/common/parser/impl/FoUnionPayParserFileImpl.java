 /** @Description 
 * @project 	poss-withdraw
 * @file 		FoParserFileImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
*/
package com.pay.fundout.reconcile.common.parser.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.fundout.reconcile.common.parser.FoParserFile;
import com.pay.fundout.reconcile.common.util.ReconcileConstants;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportRecord;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * <p>银联解析文件</p>
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see 
 */
public class FoUnionPayParserFileImpl implements
		FoParserFile {

	protected transient Log log = LogFactory.getLog(getClass());
	@Override
	public List<ReconcileImportRecord> parserFile2List(
			InputStream inputStream, ReconcileImportFile importFile)
			throws PossUntxException {
		try{
			int rowIndex = 1;
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		    HSSFSheet mainSheet = workbook.getSheetAt(0);
		    HSSFRow row = mainSheet.getRow(rowIndex);// start from the second row.
		    List<ReconcileImportRecord> arrayList = new ArrayList<ReconcileImportRecord>();
		    ReconcileImportRecord reconcileImportRecord ;
		    int allCount = mainSheet.getPhysicalNumberOfRows();
		    while (rowIndex < allCount) {
		    	
		    	reconcileImportRecord = new ReconcileImportRecord();
		    	//FILE_KY 关联导入的文件号 
		    	reconcileImportRecord.setFileId(importFile.getFileId());
		    	//BATCH_NUM	NUMBER(19)			批次号（冗余）
		    
		    	//BANK_CODE	VARCHAR2(100)			银行编号（冗余）
		    	reconcileImportRecord.setWithdrawBankId(importFile.getWithdrawBankId());
		    	//BUSI_TIME	TIMESTAMP(6)	Y		银行交易时间
				if (null != row && row.getCell(0) != null && !StringUtil.isEmpty(row.getCell(0).getStringCellValue())) {//日期时间
					reconcileImportRecord.setTradeTime(DateUtil.parse("yyyyMMdd", (row.getCell(0)).getStringCellValue()));
				}else{
				    rowIndex++;
				    row = mainSheet.getRow(rowIndex);
				    continue;
				}
				//ORDER_SEQ	NUMBER(19)		null	银行返回流水（订单流水）
				if (row.getCell(1) != null && !StringUtil.isEmpty(row.getCell(0).getStringCellValue())) {//账号流水
					reconcileImportRecord.setTradeSeq((row.getCell(1)).getStringCellValue());
				}
				//BANK_AMOUNT	NUMBER(19)			银行交易金额
				if (row.getCell(2) != null && !StringUtil.isEmpty(row.getCell(0).getStringCellValue())) {//金额
				    reconcileImportRecord.setBankAmount(
					    			new BigDecimal(
					    				row.getCell(2).getNumericCellValue())
					    					.multiply(new BigDecimal(1000))
					    					.divide(new BigDecimal(1),ReconcileConstants.DECIMAL_DIGIT_DEFAULT,BigDecimal.ROUND_HALF_UP));
				}
				//BANK_SEQ	VARCHAR2(36)	Y		银行返回流水
				if(row.getCell(4) != null && !StringUtil.isEmpty(row.getCell(0).getStringCellValue())){//银行流水
					reconcileImportRecord.setBankSeq(row.getCell(4).getStringCellValue());
				}
				//BANK_STATUS	VARCHAR2(10)			银行状态
				if(row.getCell(6) != null && !StringUtil.isEmpty(row.getCell(0).getStringCellValue())){//银行状态
					reconcileImportRecord.setBusiStatus(Long.valueOf(row.getCell(6).getStringCellValue()));
				}
		        arrayList.add(reconcileImportRecord);
		        rowIndex++;
		        row = mainSheet.getRow(rowIndex);
		    }
			return arrayList;
		}catch (Exception e) {
			log.error(this.getClass().getName()+"----------:"+e.getMessage(),e);
			throw new PossUntxException(e.getMessage(), ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}
	
		
	}
}