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

import com.pay.file.parser.dto.GatewayReconciliationParserMode

/**		
 *  @author chma
 *  @Date 2015-5-31
 *  @Description 中银卡司对账文件解析
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved.
 */
class Bocs_rc extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		FileParseResult fileParseResult = new FileParseResult();
		FileInputStream infile = new FileInputStream(file);
		
		Workbook workbook = new HSSFWorkbook(infile);
		      
		Sheet sheet = workbook.getSheetAt(0);

		BigDecimal totalDealAmount = 0;
		BigDecimal totalTransFee = 0;
		BigDecimal totalSettAmount = 0;
		
		for (int rowNum = 6; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);

			def first = String.valueOf(row.getCell(0));

			if(NumberUtil.isNumber(first)){
				GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
				def settleDate = String.valueOf(row.getCell(4));
				def channelOrderNo = String.valueOf(row.getCell(14));
				def dealDate = String.valueOf(row.getCell(5));
				def dealAmount = String.valueOf(row.getCell(6));
				def settAmount = String.valueOf(row.getCell(8));
				def transFee = String.valueOf(row.getCell(7));
				def refeNumber = String.valueOf(row.getCell(13));
				def authCode = String.valueOf(row.getCell(9));
				parserMode.setChannelOrderNo(channelOrderNo);
				parserMode.setStatus("1");
				parserMode.setDealDate(dealDate);
				parserMode.setTransFee(transFee);
				parserMode.setDealAmount(dealAmount);
				parserMode.setSettlementRate("");
				parserMode.setSettAmount(settAmount);
				parserMode.setRefeNumber(refeNumber);
				parserMode.setSettleDate(settleDate);
				parserMode.setAuthCode(authCode);
				println dealAmount;
				totalDealAmount = totalDealAmount.add(new BigDecimal(dealAmount));
				totalTransFee = totalTransFee.add(new BigDecimal(transFee));
				totalSettAmount = totalSettAmount.add(new BigDecimal(settAmount));
				
				fileParseResult.addItem(parserMode);
			}	
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		fileParseResult.setTotalTransFee(totalTransFee);
		fileParseResult.setTotalSettAmount(totalSettAmount);
		
		println totalDealAmount;
		return fileParseResult;
	}

}