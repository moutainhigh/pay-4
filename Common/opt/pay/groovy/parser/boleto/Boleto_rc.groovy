
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
 *  @author  * 
 *  @Description boleto 对账模版解析器
 */
class Boleto extends AbstractBaseFileParser {

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
			count++;00 
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			String[] _line= readLine.split("\\,"); // 分隔
			GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
			
			//def settleDate = _line[6];
			def channelOrderNo = _line[4];
			def dealDate = _line[3];
			def transCurr = _line[7];
			def dealAmount = _line[8];	
			def settlementRate = _line[9];			
			def transFee = _line[11];
			def merchantName = _line[16];	
							
			
			println channelOrderNo;
			println dealDate;
			println dealAmount;
			println transFee;
			println transCurr;
			println merchantName;
			
			parserMode.setMerchantName(merchantName);
			parserMode.setTransCurrency(transCurr);
			parserMode.setChannelOrderNo(channelOrderNo);
			parserMode.setStatus("1");
			parserMode.setDealDate(turnDate(dealDate,"yyyy/MM/dd"));
			parserMode.setTransFee(String.valueOf(transFee));
			parserMode.setDealAmount(dealAmount);	
			parserMode.setSettlementRate(settlementRate);			
			//println dealAmount;
			totalDealAmount = totalDealAmount.add(new BigDecimal(dealAmount));
			totalTransFee = totalTransFee.add(new BigDecimal(transFee));
			//totalSettAmount = totalSettAmount.add(new BigDecimal(settAmount));
			
			fileParseResult.addItem(parserMode);

		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		fileParseResult.setTotalTransFee(totalTransFee);
		//fileParseResult.setTotalSettAmount(totalSettAmount);
		
		println totalDealAmount;
		return fileParseResult;
	}
	
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
