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
 *  @Description 拒付文件解析
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved.
 */
class Chargeback extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		FileParseResult fileParseResult = new FileParseResult();
		FileInputStream infile = new FileInputStream(file);
		
		HSSFWorkbook  workbook = new HSSFWorkbook(infile);
		      
		HSSFSheet sheet = workbook.getSheetAt(0);

		BigDecimal totalDealAmount = 0;
		
		for (int rowNum = 7; rowNum <= sheet.getLastRowNum(); rowNum++) {
			HSSFRow row = sheet.getRow(rowNum);

			def first = String.valueOf(row.getCell(0));

			if(NumberUtil.isDouble(first)){

				BouncedResultDTO parserMode = new BouncedResultDTO();

				def refNo = String.valueOf(row.getCell(1));
				def cardNo = String.valueOf(row.getCell(2));
				def merchantCode = String.valueOf(row.getCell(3));
				def merchantName = String.valueOf(row.getCell(4));
				//def tradeDate = String.valueOf(row.getCell(5));
				def currencyCode = String.valueOf(row.getCell(6));
				def tradeAmount = String.valueOf(row.getCell(7));
				def cpCurrencyCode = String.valueOf(row.getCell(8));
				def chargeBackAmount = String.valueOf(row.getCell(9));
				def authorisation = String.valueOf(row.getCell(10));
				def chargeBackMsg = String.valueOf(row.getCell(11));
				def oldRefNo = String.valueOf(row.getCell(12));
				def cpdDate = String.valueOf(row.getCell(13));

				//Date tradeDate = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(row.getCell(5));

				def tradeDate = row.getCell(5).getDateCellValue();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				parserMode.setRefNo(refNo);
				parserMode.setCardholderCardno(cardNo);
				parserMode.setBussinessNo(merchantCode);
				parserMode.setBussinessName(merchantName);
				parserMode.setStranDate(sdf.format(tradeDate));
				parserMode.setCurrencyCode(currencyCode);
				parserMode.setSorderAmount(tradeAmount);
				parserMode.setBankCurrencyCode(cpCurrencyCode);
				parserMode.setSbankAmount(chargeBackAmount);
				parserMode.setAuthorisation(authorisation);
				parserMode.setReasonCode(chargeBackMsg);
				parserMode.setBouncedReason(chargeBackMsg);

				parserMode.setOldRefNo(oldRefNo);
				parserMode.setCpdDate(cpdDate);
				
				totalDealAmount = totalDealAmount.add(new BigDecimal(chargeBackAmount));
				
				fileParseResult.addItem(parserMode);
			}	
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		
		return fileParseResult;
	}

}