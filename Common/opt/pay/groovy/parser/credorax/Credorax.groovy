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

import com.pay.file.parser.dto.GatewayReconciliationParserMode

/**		
 *  @author 
 *  @Date 2011-8-22
 *  @Description Credorax 对账模版解析器
 *  modify by nico.shao 2016-06-21  修改处：手续费相关处，清算金额
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
			String[] _line= readLine.split("\\,"); // 分隔
			GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
			
			def settleDate = _line[6];
			def channelOrderNo = _line[18];
			def dealDate = _line[5];
			def dealAmount = _line[9];	//[9]这个是 原始金额 
			
			//def settAmount = _line[26];	//[32] 才是清算金额吧 
			//def transFee = _line[31] ;	//这个是百分比手续费 
			//modify by nico.shao 2016-06-21
			def settAmount = _line[32];	//清算净额
			def fixedFee = _line[27];	//固定手续费
			def perFee = _line[31];		//清算手续费
			def settlemntDealAmount = _line[26];	//以清算货币计价的交易金额
			def settlemntCurrency = _line[25];				

			BigDecimal transFee = 0;
			transFee = transFee.add(new BigDecimal(fixedFee)).add(new BigDecimal(perFee));
			//def transFee = _line[31] + _line[27] ;	//这个是百分比手续费 
		
			def type= _line[7];
			def transCurr = _line[8];
			
			def acctAmountGross = _line[10];	//这个
			def authCode =_line[21];
			def merchantName = _line[0];

			def interchangeAmount = _line[28];
			def debitOrCreditCard = _line[12];


		//	def refeNumber = _line[15];	？			
		//	def merchantNo= _line[0];？
		//	def credoraxStatus= _line[6];？
		//	def acctCurr = _line[9];？
		//	def captureMethod = _line[13];？
		//	def captureMethod2 = _line[14];？
		//	def capture=captureMethod+","+captureMethod2;
		//	def acquirerRef = _line[16];？
		//	def transactionCountry = _line[19];？
		//	def areaOfEvent = _line[20];？
		//	def fpi = _line[21];？
		//	def feePercentage = _line[22];?
		//	def base = _line[23];?
		//	def merchantCity = _line[27];?


			parserMode.setDebitOrCreditCard(debitOrCreditCard);
			parserMode.setInterchangeAmount(interchangeAmount);
			parserMode.setMerchantName(merchantName);
			parserMode.setAuthCode(authCode); 
			parserMode.setAcctAmountGross(acctAmountGross);
			parserMode.setTransCurrency(transCurr);
			parserMode.setType(type);
		
			parserMode.setChannelOrderNo(channelOrderNo);
			parserMode.setStatus("1");
			parserMode.setDealDate(turnDate(dealDate,"yyyy/MM/dd HH:mm"));
			parserMode.setTransFee(String.valueOf(transFee));
			parserMode.setDealAmount(dealAmount);
			parserMode.setSettlementRate("");
			parserMode.setSettAmount(settAmount);
			parserMode.setSettlementCurrency(settlemntCurrency);
			parserMode.setSettlementAmount(settlemntDealAmount);
			parserMode.setPerFee(perFee);			
			parserMode.setFixedFee(fixedFee);

		//	parserMode.setMerchantCity(merchantCity);
		//	parserMode.setInterchangeCurrency(interchangeCurrency);
		//	parserMode.setBase(base);
		//	parserMode.setAcquirerRef(acquirerRef);
		//	parserMode.setFeePercentage(feePercentage);
		//	parserMode.setFpi(fpi);
		//	parserMode.setAreaOfEvent(areaOfEvent);
		//	parserMode.setTransactionCountry(transactionCountry);
		//	parserMode.setMerchTranRef(merchTranRef);
		//	parserMode.setCaptureMethod(capture);
		//	parserMode.setAcctCurrency(acctCurr);
		//	parserMode.setCredoraxStatus(credoraxStatus);												
		//	parserMode.setMerchantNo(merchantNo);
		//	parserMode.setRefeNumber(refeNumber);

			parserMode.setSettleDate(turnDate(settleDate,"yyyy/MM/dd"));

			//println dealAmount;
			totalDealAmount = totalDealAmount.add(new BigDecimal(dealAmount));
			totalTransFee = totalTransFee.add(new BigDecimal(transFee));
			totalSettAmount = totalSettAmount.add(new BigDecimal(settAmount));
			
			fileParseResult.addItem(parserMode);

		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		fileParseResult.setTotalTransFee(totalTransFee);
		fileParseResult.setTotalSettAmount(totalSettAmount);
		
		println totalDealAmount;
		return fileParseResult;
	}
	
	// 如果是 strdate= 2016/5/23 这个函数会出错  crodex 就是这种格式的
	public String turnDate(String strdate,String strfmt)  {
		
		DateFormat fm = new SimpleDateFormat(strfmt);		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String format;
		try {
			format = formatter.format(fm.parse(strdate));
			return format;
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}			
	}

}
