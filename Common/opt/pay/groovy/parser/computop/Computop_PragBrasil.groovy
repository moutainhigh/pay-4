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

import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat

import com.pay.file.parser.dto.GatewayReconciliationParserMode

/**		
 *  @author davis.guo   
 *  @Date 2016-07-24
 *  @Description Computop 针对PragBrasil支付对账文件解析
 *  @Copyright Copyright © 2014-2023 ipaylinks.com . All rights reserved.
 *  xsl 文件，行数和 cell 都是从0 开始计数的
 */
class Computop_PragBrasil extends AbstractBaseFileParser {

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		println "PragBrasil";
		FileParseResult fileParseResult = new FileParseResult();
		FileInputStream infile = new FileInputStream(file);
		
		Workbook workbook = new HSSFWorkbook(infile);
		      
		Sheet sheet = workbook.getSheetAt(0);//Sheet1
		
		int totalRecord = 0;//总记录数
		BigDecimal totalDealAmount = 0;//总的交易金额
		BigDecimal totalTransFee = 0;//总的手续费
		BigDecimal totalSettAmount = 0;//总的结算金额
		
		int rowSize = sheet.getLastRowNum();
		//交易记录处理
		for (int rowNum = 3; rowNum < rowSize-2; rowNum++) {//从第4行开始
			Row row = sheet.getRow(rowNum);
			if(row == null)continue;
			
			def debit_credit_chargebac = String.valueOf(row.getCell(1));	//RECORD_TYPE{700:“支付” ，701:“退款”，702“拒付”) 
			def channelOrderNo = String.valueOf(row.getCell(2));	//TRANSACTION_ID渠道号
			def currency = String.valueOf(row.getCell(3));			//TRANSACTION_CURRENCY交易币种
			def dealAmount = String.valueOf(row.getCell(5));		//TRANSACTION_AMOUNT交易金额
			def dealDate = String.valueOf(row.getCell(6));			//PAYMENT_CREATION_DATE支付时间
			
			debit_credit_chargebac = (debit_credit_chargebac==null?"":debit_credit_chargebac);
			channelOrderNo = (channelOrderNo==null?"":channelOrderNo);
			currency = (currency==null?"":currency);
			dealAmount = (dealAmount==null?"":dealAmount);
			dealDate = (dealDate==null?"":dealDate);
			dealDate = changeDate(dealDate);
			
			GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
	
			println "debit_credit_chargebac= " + debit_credit_chargebac + "\t" + "channelOrderNo= " + channelOrderNo + "\t" + "currency= " + currency + "\t" + "dealAmount= " + dealAmount + "\t" + "dealDate= " + dealDate;

			if(debit_credit_chargebac!=null && debit_credit_chargebac.equals("700")){//“支付”
				println "支付"				
				//正向交易（渠道订单）对账时，只核对交易金额和币种。
				/* 有用的信息：
				 * 渠道订单号:reconciliationDto.getChannelOrderNo();
				 * 订单金额:reconciliationDto.getDealAmount()
				 * 对账记录状态:reconciliationDto.getResultCode();注：在ReconcileFileServiceController.upload()中会赋值,reconciliationDto.setResultCode("0000");
				 * 结算币种：reconciliationDto.getSettlementCurrencyCodes()注：在ReconcileFileServiceController.upload()中会赋值,reconciliationDto.setSettlementRate(model.getSettlementRate());
				 * */
				/*
				 * String dealAmount = reconciliationDto.getDealAmount();//银行交易金额（账目原数据）
				 * Long payAmount = channelOrderDTO.getPayAmount();//我司渠道订单支付金额
					BigDecimal dAmt = new BigDecimal(dealAmount).multiply(new BigDecimal("100"));//银行交易金额*100。注意：转换时，账目原数据要/100。即对账公式：我司渠道订单支付金额=dealAmount/100
					if (Math.abs(payAmount/10 - dAmt.longValue()) > 1) {金额不匹配}
				 * */
				 //如果需要对渠道订单做相应的处理，则在此处理　TODO
			}else if(debit_credit_chargebac!=null && debit_credit_chargebac.equals("701")){//“退款”
				println "退款"
				//反向交易（退款订单）对账时，只核对交易金额
				/*
				 * String dealAmount = reconciliationDto.getDealAmount();//银行退款金额（账目原数据）
				 * String transferRate = channelOrderDTO.getTransferRate();//支付汇率
				Long refundAmount = refundOrder.getRefundAmount();//我司退款订单金额
				BigDecimal reconAmt = new BigDecimal(refundAmount)
				.divide(new BigDecimal("1000")).multiply(new BigDecimal(transferRate));//对账公式为：退款订单中的退款金额*渠道订单中的转换汇率=红色框的金额数字

				BigDecimal dAmt = new BigDecimal(dealAmount).abs();//取绝对值
				// add by mmzhang 农行对账单退款金额为正其他渠道都为负数，这里要进行处理
				if (Math.abs(reconAmt.longValue() - dAmt.longValue()) > 1) {金额不匹配}
				*/
				 //如果需要对渠道订单做相应的处理，则在此处理　TODO
			}else if(debit_credit_chargebac!=null && debit_credit_chargebac.equals("702")){//“拒付”
				println "拒付"
				 //如果需要对渠道订单做相应的处理，则在此处理　TODO
			}

			parserMode.setChannelOrderNo(channelOrderNo);
			parserMode.setStatus("1");//状态，1-成功，0-失败。
			parserMode.setDealDate(dealDate);
			parserMode.setDealAmount(dealAmount);
			parserMode.setSettlementCurrency(currency);

			//TODO 这种对账单中包含了三种订单类型，是否要分开统计总额？
			//totalDealAmount = totalDealAmount.add(new BigDecimal(dealAmount));

			fileParseResult.addItem(parserMode);	
		}
		Row row = sheet.getRow(rowSize-2);
		if(row != null)
		{
			def total_deal_amount = String.valueOf(row.getCell(6));	//交易总金额
			total_deal_amount = (total_deal_amount==null?"0":total_deal_amount);
			totalDealAmount = new BigDecimal(total_deal_amount);
			
			def total_record = String.valueOf(row.getCell(4));	//交易记录数
			total_record = (total_record==null?"0":total_record);
			totalRecord = (int)Float.parseFloat(total_record);
		}
		
		fileParseResult.setTotalDealAmount(totalDealAmount);
		//fileParseResult.setTotalTransFee(totalTransFee);
		//fileParseResult.setTotalSettAmount(totalSettAmount);
		fileParseResult.setTotalRecord(totalRecord);
		
		println totalDealAmount + "\t" + totalRecord;
		
		return fileParseResult;
	}

	//日期格式的转换"02.09.2014 10:55:33"=>"02.09.2014 10:55:33"
	public String changeDate(String datetime){
		
		String date_time = "";
		SimpleDateFormat sdf_origin = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
		SimpleDateFormat sdf_convert = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		if (!datetime.equals(""))
		{
			Date dt = sdf_origin.parse(datetime)
			println dt
			date_time = sdf_convert.format(dt)
		}
		println date_time

		return date_time;
	}

}