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

import com.pay.file.parser.AbstractBaseFileParser
import com.pay.file.parser.dto.FileParseResult
import com.pay.util.StringUtil
import com.pay.util.NumberUtil

import java.text.SimpleDateFormat

import com.pay.poss.controller.fi.dto.BouncedResultDTO;

/**		
 *  @author mmzhang
 *  @Date 2016-06-07
 *  @Description 拒付文件解析
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved.
 */
class Chargeback extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		FileParseResult fileParseResult = new FileParseResult();
		FileInputStream infile = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(infile);
		      
		Sheet sheet = workbook.getSheetAt(0);

		BigDecimal totalDealAmount = 0;
		
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			println "row.getCell(0)"+row.getCell(0);
			if((null !=String.valueOf(row.getCell(0)) && !"".equals(String.valueOf(row.getCell(0)).trim() ) )){

				BouncedResultDTO parserMode = new BouncedResultDTO();
								
				def chargeBackMsg = String.valueOf(row.getCell(1));			
				def chargeBackAmount = String.valueOf(row.getCell(2));
				def merchantCode = String.valueOf(row.getCell(3));
				def merchantName = String.valueOf(row.getCell(4));
				
				def tradeDate = row.getCell(5).getDateCellValue();
				
				def cardNo = String.valueOf(row.getCell(7));
				def authorisation = String.valueOf(row.getCell(8));
				def tradeAmount = String.valueOf(row.getCell(9));
				def refNo = String.valueOf(row.getCell(10));
																								
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				def trandate=sdf.format(tradeDate);
				parserMode.setRefNo(refNo);
				parserMode.setCardholderCardno(cardNo);
				parserMode.setBussinessNo(merchantCode);
				parserMode.setBussinessName(merchantName);
				parserMode.setStranDate(trandate);
							
				parserMode.setSorderAmount(tradeAmount);
				parserMode.setBankCurrencyCode("USD");
				parserMode.setSbankAmount(chargeBackAmount);
				parserMode.setAuthorisation(authorisation);
				parserMode.setReasonCode(chargeBackMsg);
				parserMode.setBouncedReason(chargeBackMsg);
			
			
				totalDealAmount = totalDealAmount.add(new BigDecimal(chargeBackAmount));
						
				fileParseResult.addItem(parserMode);
				
		}	
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		
		return fileParseResult;
	}

}