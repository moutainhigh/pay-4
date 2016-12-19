/**
 *  File: Credorax.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   liwei     Create
 *
 */
package com.pay.fundout.bankfile.parser.groovy

import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.FileReader

import java.math.BigDecimal
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.FileInputStream

import com.pay.file.parser.AbstractBaseFileParser
import com.pay.file.parser.dto.FileParseResult
import com.pay.poss.controller.fi.dto.BouncedResultDTO;

/**		
 *  @author 
 *  @Date 2016年5月26日11:01:57
 *  @Description Credorax 拒付对账模版解析器
 */
class Credorax extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
	
		FileParseResult fileParseResult = new FileParseResult();		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int count = 0;
		int columnCount = 0;

		String readLine = reader.readLine();
		
		BigDecimal totalDealAmount = 0;
		BigDecimal totalTransFee = 0;
		BigDecimal totalSettAmount = 0;
		
		while((readLine = reader.readLine()) != null) {
			count++;
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String[] _line= readLine.split("\\,"); // 分隔
			BouncedResultDTO parserMode = new BouncedResultDTO();
			def tradeDate =  _line[4];
			tradeDate=tradeDate.substring(1, tradeDate.length()-1);
			def bankCurrencyCode = _line[7];
			def sbankAmount = _line[8];
			sbankAmount=sbankAmount.substring(0, sbankAmount.length()-3);
			def channelOrderNo = _line[9];
			def cardholderCardno = _line[14];
			def reasonCode = _line[15];
			reasonCode=(reasonCode.substring(1, reasonCode.length()-1)).trim();
			def bouncedReason = _line[16];				
			parserMode.setChannelOrderNo(channelOrderNo);
			parserMode.setBankCurrencyCode(bankCurrencyCode);	
			parserMode.setSbankAmount(sbankAmount);	
			parserMode.setCardholderCardno(cardholderCardno);		
			parserMode.setReasonCode(reasonCode);
			parserMode.setBouncedReason(bouncedReason);	
			parserMode.setStranDate(sdf.format(sdf.parse(tradeDate)));		
			totalDealAmount = totalDealAmount.add(new BigDecimal(sbankAmount));						
			fileParseResult.addItem(parserMode);
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		println totalDealAmount;
		return fileParseResult;
	}
	
	

}
