package com.pay.file.parser.groovy

import java.io.InputStreamReader
import java.io.BufferedReader

import java.math.BigDecimal

import java.io.FileInputStream

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.file.parser.AbstractBaseFileParser
import com.pay.file.parser.dto.FileParseResult
import com.pay.util.StringUtil
import com.pay.util.NumberUtil
import com.pay.poss.controller.fi.dto.BouncedResultDTO;

import java.text.SimpleDateFormat

import com.pay.file.parser.dto.ChargeBackDTO

/**		
 *  @author chma
 *  @Date 2015-11-22
 *  @Description 农行拒付文件解析
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved.
 */
class Chargeback extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		FileParseResult fileParseResult = new FileParseResult();
		FileInputStream infile = new FileInputStream(file);
		
		Workbook  workbook = new HSSFWorkbook(infile);
		      
		Sheet sheet = workbook.getSheetAt(0);

		BigDecimal totalDealAmount = 0;
		
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);

			def first = String.valueOf(row.getCell(0));

			if(NumberUtil.isDouble(first)){
				BouncedResultDTO parserMode = new BouncedResultDTO();
				def refNo = String.valueOf(row.getCell(1));
				def cardNo = String.valueOf(row.getCell(7));				
				def merchantCode = String.valueOf(row.getCell(4));				
				def merchantName = String.valueOf(row.getCell(6));			
				def chargeBackAmount = String.valueOf(row.getCell(8));
				def reasonCode = String.valueOf(row.getCell(10));	
				def chargeBackMsg = String.valueOf(row.getCell(10));					
				def authorisation = String.valueOf(row.getCell(9));				
				def cpdDate = String.valueOf(row.getCell(2));
				def tradeDate = String.valueOf(row.getCell(3));
				
				
				parserMode.setRefNo(refNo);
				parserMode.setCardholderCardno(cardNo);
				parserMode.setBussinessNo(merchantCode);
				parserMode.setBussinessName(merchantName);
				parserMode.setStranDate(tradeDate);			
				parserMode.setSbankAmount(chargeBackAmount);
				parserMode.setAuthorisation(authorisation);
				parserMode.setReasonCode(reasonCode);
				parserMode.setBouncedReason(chargeBackMsg);
				
				
				
				fileParseResult.addItem(parserMode);
			}	
		}
		
		
		return fileParseResult;
	}

}