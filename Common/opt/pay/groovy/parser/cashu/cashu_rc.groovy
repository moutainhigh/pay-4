package com.pay.file.parser.groovy

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook


import com.pay.file.parser.AbstractBaseFileParser
import com.pay.file.parser.dto.FileParseResult

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.pay.file.parser.dto.GatewayReconciliationParserMode

/**		
 *  @author
 *  @Date
 *  @Description Cashu收款对账文件
 */
class Cashu_rc extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		FileInputStream input = new FileInputStream(file);
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		//GatewayOrderQueryService gatewayOrderQueryService = (GatewayOrderQueryService)wac.getBean("gatewayOrderQueryService");
		Workbook workbook = new HSSFWorkbook(new BufferedInputStream(input));       
		Sheet sheet = workbook.getSheetAt(0);
		FileParseResult fileParseResult = new FileParseResult();
		BigDecimal totalDealAmount = 0;
		BigDecimal totalTransFee = 0;
		BigDecimal totalSettAmount = 0;
		for (int rowNum = 11; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(String.valueOf(row.getCell(3)).startsWith("Refund on transaction")){
				continue;
			}
			Row nextRow = sheet.getRow(rowNum+1);
			GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
			def settleDate = String.valueOf(row.getCell(0));
			//def channelOrderNo = row.getCell(0);
			//def dealDate = _line[2];
			def dealAmount = String.valueOf(nextRow.getCell(5));
			def transFee = String.valueOf(row.getCell(6));
			def refeNumber = String.valueOf(row.getCell(1));
			def channelOrderNo = String.valueOf(nextRow.getCell(2));
			println dealAmount;
			println transFee;
			def settAmount = new BigDecimal(dealAmount).subtract(new BigDecimal(transFee)).toString();
			println settAmount;
			println "-----------------";		
			parserMode.setChannelOrderNo(channelOrderNo);
			parserMode.setStatus("1");
			//parserMode.setDealDate(dealDate);
			parserMode.setTransFee(String.valueOf(transFee));
			parserMode.setDealAmount(String.valueOf(dealAmount));
			parserMode.setSettlementRate("");
			parserMode.setSettAmount(settAmount);
			parserMode.setRefeNumber(new BigDecimal(refeNumber).longValue()+"");
			parserMode.setSettleDate(dateToString(settleDate));
			
			//println dealAmount;
			totalDealAmount = totalDealAmount.add(new BigDecimal(dealAmount));
			totalTransFee = totalTransFee.add(new BigDecimal(transFee));
			totalSettAmount = totalSettAmount.add(new BigDecimal(settAmount));
			
			fileParseResult.addItem(parserMode);
			rowNum++;
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		fileParseResult.setTotalTransFee(totalTransFee);
		fileParseResult.setTotalSettAmount(totalSettAmount);
		
		println totalDealAmount;
		return fileParseResult;
	}
	
	
	public  String dateToString(String dateStr) {
		String pattern="dd/MM/yyyy HH:mm";
		String pattern2="yyyy-MM-dd HH:mm";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date date = formatter.parse(dateStr);
			 SimpleDateFormat formatter2 = new SimpleDateFormat(pattern2);
		     return formatter2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}        
	}
	

}
