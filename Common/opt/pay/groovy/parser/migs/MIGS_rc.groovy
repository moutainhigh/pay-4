/**
 *  File: Credorax.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016-05-26  sch       for migs 格式1，前面10行，后面的是两行一条记录
 *  2016-05-27  sch       把migs 格式1 和 migs 格式2 统一到一个文件中了，分别用两个函数来实现 这样修改的话，只需要修改这个文件即可。 
 *			  代码上有很多重复的地方，暂时不做任何优化。
 *  2016-08-02  sch	  migs 格式1，有换页符号，需要去掉这个换页符 "1 "。 从规律上讲是第55、110、165行等。可以增加一个判断。  
 *  2016-08-15  sch       修改为异步模式，并且把文件存储字符串改为 GB2312 (utf-8 在nico.shao 的机器上 比较中文出错了） 
 *  (1) 格式说明: 
 * 
 *		格式		卡号	   日期		正向交易	退款			总列数		商户名称		数据
 *		Migs 1		带****	  2016/05/23	PMIG		RMIG/无授权码		14             （线上外卡 MIGS ）      前面10行文本，每条数据由两行来表示
 *															
 *		Migs 2		全部卡号  20160408	PMIG		RMIG/无授权码		13	       （启赟线上外卡 MOTO ）  前面8行文本，每条数据1行
 *
 *		正常Moto        全部卡号  20160406	PCEP 	        REFP/有授权码		13	       （启赟线上外卡 MOTO ）  前面8行文本，每条数据1行
 *
 */
package com.pay.file.parser.groovy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import java.io.FileInputStream;

import com.pay.util.StringUtil;

import com.pay.file.parser.AbstractBaseFileParser;
import com.pay.file.parser.dto.FileParseResult;

import com.pay.file.parser.dto.GatewayReconciliationParserMode;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.dto.MIGSRefundOrderQueryModel;	//migs RefundOrderQueryModel 


/**		
 *  @author 
 *  @Date 2011-8-22
 *  @Description Adyen 对账模版解析器
 * 
 */
class MIGS_rc extends AbstractBaseFileParser {

	/*
	*  这个函数根据第2行的是否 有 “网上” 这两个字来判断是 migs 格式1，还是格式2，并且根据第7行或者第9行是否----\===== 来辅助判断 
	*/

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		//for test
		int mode = 0;		
		
		//判断是格式1，还是格式2，还是有错误
		//我们判断的标准是 
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GB2312");       
			BufferedReader reader = new BufferedReader(read);
			String readLine = reader.readLine();
			int index = 0;
			while((readLine = reader.readLine()) != null) {
				index++;
				if(index==1){
				    if(readLine.indexOf("网上商户 POS 交易清算明细表") != -1){
					 mode =1;
				    }
				    else if(readLine.indexOf(" 商户 POS 交易清算明细表") != -1){		//前面加一个空格，特别指定
					 mode =2;
				    }
				}
			
				//增加一下判断
				if((index==6 ) && (mode >0)) {
					if( !(readLine.trim().startsWith("-----"))){
						println "第六行必须为 -----开头";
						mode=0;	
					}
				}

				if(index==9){
					if(mode==1){
						if( !(readLine.trim().startsWith("======="))){
							println "mode 1 line9 必须是===== 开头的";
							mode=0;							
						}
					}else if(mode==2) {
						if((readLine.trim().startsWith("======="))){
							println "mode 2 line9 不能有===== 开头的的行";
							mode=0;							
						}
					}
				}
 
				if(index>=9)
					break;		
			}
			reader.close();
			
			if(mode==1){
				return parserFile_Mode1(file);		//migs格式1 
			}
			if(mode==2){
				return parserFile_Mode2(file);		//migs格式2
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		FileParseResult fileParseResult = new FileParseResult();
		return fileParseResult;
	}


	//这个函数是 Migs的代码1 ，开头10行，中间是两行的格式 
	public FileParseResult parserFile_Mode1(File file) throws Exception {
		println "mode1";

		FileParseResult fileParseResult = new FileParseResult();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		GatewayOrderQueryService gatewayOrderQueryService = (GatewayOrderQueryService)wac.getBean("gatewayOrderQueryService");

		
		BigDecimal totalDealAmount = 0;
		BigDecimal totalTransFee = 0;
		BigDecimal totalSettAmount = 0;
		
		int hasHeji=0;		//已经出现合计 这个条目了	
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GB2312");       
			BufferedReader reader = new BufferedReader(read);
			String readLine = reader.readLine();
			int index = 0;	//这个是行数
			List<String> totalData = new ArrayList<String>();

			List<MIGSRefundOrderQueryModel> migsROQModeList2 = new ArrayList<MIGSRefundOrderQueryModel>();  //方法2
			Integer  migsRefundIndex = 0;
			while((readLine = reader.readLine()) != null) {
				//println readLine;
				
				if(index>8){		//总共是10行，去掉第一行，从零开始计算，是8就对了
					//println readLine;		
					
					//处理换行符  2016-08-02
					if(readLine.startsWith("1 ")){
						//最好判断一下index 是55的整数倍
						readLine = readLine.substring(1,readLine.length());
					}
					//end 2016-08-02

					GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
					if(readLine.trim().startsWith("小计"))continue;
					if(readLine.trim().startsWith("-----"))continue;
					if(readLine.trim().startsWith("合计")) {
						hasHeji = 1;
						continue;
					}

					if( (hasHeji==1) && (readLine.trim().startsWith("PMIG"))){
						println "read pmig ===>break" ;
						String[] data = readLine.split(" ");
						for(String column : data){
							if(!StringUtil.isEmpty(column)){
								//需要去掉 ',' 
								String column_flit = column.replace(",",""); 
								totalData.add(column_flit);
							}
						}
						break;
					}
					
					//已经出现合计字样，就不在继续解析了，有可能没有"PMIG这个条目" 从而导致下面继续解析 
					if(hasHeji==1) {
						println "已经有合计了";
						continue;
					}

					//再读一行 add by sch 2016-05-26
					String readLine2 = reader.readLine();
					if(readLine2 == null){
						println "格式错误"
						break;
					}
					
					//处理换行符  2016-08-02
					if(readLine.startsWith("1 ")){
						//最好判断一下index 是55的整数倍
						readLine = readLine.substring(1,readLine.length());
					}
					//end 2016-08-02

					//println readLine2 ;
					readLine = readLine + readLine2;  //两行合成一行
					println "two line ==>oneline" + readLine ;

					List<String> record = new ArrayList<String>();
					String[] data = readLine.split(" ");
					for(String column : data){
						if(!StringUtil.isEmpty(column)){
							record.add(column);
						}
					}
					//println "step1";
					if(record.size()< 10){
						println "解析出错，一行少于 参考号的编码 " + record.size() ;
						println readLine;

						break;
					}
					
					//println "step2";
					//由于没有授权码，所以RMIG 变成第8项目了 
					String dealAmount = changeAmount(record.get(5));
					String transFee   = changeAmount(record.get(6));
					String settAmount = changeAmount(record.get(7));
					String transDate = record.get(3).replace("/","");	//统一格式为 20160513

					if( (record.get(9).trim().equals("RMIG")) || (record.get(8).trim().equals("RMIG")) ) {
						
						//println "第8项=[" + record.get(8) + "]";   //打印结果:  第8项=[RMIG]	
						//下面假设 参考号就是第11 项目 
						
						int referenceNoIndex = 10;		
						if(record.size()<= referenceNoIndex ){
							continue;
						}
						
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");	 //如果为空，会被过滤掉
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>referenceNoIndex?record.get(referenceNoIndex):null);
						parserMode.setDealDate(transDate);
						
						//parserMode.setAuthCode(record.get(8)); //退款订单，没有授权码
						parserMode.setTradeType("2");   //退款订单
						parserMode.setTransCurrency("CNY");
						parserMode.setSettlementCurrency("CNY");
						parserMode.setSettlementRate("1");

						fileParseResult.addItem(parserMode);
					}
					else 
					{						
						int referenceNoIndex = 11;		//Migs 版本 1 为 11，  Migs 版本2 为 12 
						if(record.size()<= referenceNoIndex ){
							continue;
						}

						
						//println "step3";
						
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");	 //如果为空，会被过滤掉
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>referenceNoIndex?record.get(referenceNoIndex):null);
						parserMode.setDealDate(transDate);
						//add by nico.shao 2016-08-15
						parserMode.setAuthCode(record.get(8));
						parserMode.setTradeType("1");   //支付订单
						parserMode.setTransCurrency("CNY");
						parserMode.setSettlementCurrency("CNY");
						parserMode.setSettlementRate("1"); 
						//2016-08-15
						fileParseResult.addItem(parserMode);
					}
				}
				index++;
			}
			reader.close();
			

			if(totalData.size()>=5) {
				println "totalDatasize>=5  " + totalData.get(2) + "   "+ totalData.get(3) + "   " + totalData.get(4);
				totalDealAmount = new BigDecimal(totalData.get(2));
				totalTransFee = new BigDecimal(totalData.get(3));
				totalSettAmount = new BigDecimal(totalData.get(4));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		fileParseResult.setTotalTransFee(totalTransFee);
		fileParseResult.setTotalSettAmount(totalSettAmount);
		fileParseResult.setCommand("pre");
		
		println totalDealAmount;
		return fileParseResult;
	}

	//这个格式是类似于moto的格式 ,每条语句是一行 
	//开头是一行 
	public FileParseResult parserFile_Mode2(File file) throws Exception{
		
		println "mode1";

		FileParseResult fileParseResult = new FileParseResult();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		GatewayOrderQueryService gatewayOrderQueryService = (GatewayOrderQueryService)wac.getBean("gatewayOrderQueryService");
		//BufferedReader reader = new BufferedReader(new FileReader(file));
		//String readLine = reader.readLine();
		
		BigDecimal totalDealAmount = 0;
		BigDecimal totalTransFee = 0;
		BigDecimal totalSettAmount = 0;
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GB2312");       
			BufferedReader reader = new BufferedReader(read);
			String readLine = reader.readLine();
			int index = 0;
			int hasHeji=0;		//已经出现合计 这个条目了		
			List<String> totalData = new ArrayList<String>();

			List<MIGSRefundOrderQueryModel> migsROQModeList2 = new ArrayList<MIGSRefundOrderQueryModel>();  //方法2
			Integer  migsRefundIndex = 0;

			while((readLine = reader.readLine()) != null) {
				if(index>6){
					//println readLine;

					GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
					if(readLine.trim().startsWith("小计"))continue;
					if(readLine.trim().startsWith("-----")) continue;					
					if(readLine.trim().startsWith("合计")) {
						hasHeji = 1;
						continue;
					}
					if( (hasHeji==1) && (readLine.trim().startsWith("PMIG"))){
						println "read pmig ===>break" ;
						String[] data = readLine.split(" ");
						for(String column : data){
							if(!StringUtil.isEmpty(column)){
							   //需要去掉 ',' 
							   String column_flit = column.replace(",",""); 
							   totalData.add(column_flit);
							}
						}
						break;
					}
					
					//已经出现合计字样，就不在继续解析了，有可能没有"PMIG这个条目" 从而导致下面继续解析 
					if(hasHeji==1) {
						println "已经有合计了";
						continue;
					}
					

					List<String> record = new ArrayList<String>();
					String[] data = readLine.split(" ");
					for(String column : data){
						if(!StringUtil.isEmpty(column)){
							 record.add(column);
							 //String column_flit = column.replace(",",""); 
							 //record.add(column_flit);
						}
					}

					if(record.size()<11){
						println "该行字段数目<12" ;
						continue;
					}
					
					//由于没有授权码，所以RMIG 变成第8项目了 
					
					String dealAmount = changeAmount(record.get(5));
					String transFee   = changeAmount(record.get(6));
					String settAmount = changeAmount(record.get(7));
					String transDate = record.get(3);	//统一格式为 20160513

					if( (record.get(9).trim().equals("RMIG")) || (record.get(8).trim().equals("RMIG")) ) {
						
						//println "第8项=[" + record.get(8) + "]";   //打印结果:  第8项=[RMIG]	
						//下面假设 参考号就是第11 项目 
						int referenceNoIndex = 11;		
						if(record.size()<= referenceNoIndex ){
							continue;
						}						
						
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");		//如果为空，会被过滤掉
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>referenceNoIndex?record.get(referenceNoIndex):null);
						parserMode.setDealDate(transDate);
						//add by nico.shao 2016-08-15
						//parserMode.setAuthCode(record.get(8)); //退款订单，没有授权码
						parserMode.setTradeType("2");   //退款订单
						parserMode.setTransCurrency("CNY");
						parserMode.setSettlementCurrency("CNY");
						parserMode.setSettlementRate("1");

						//end nico.shao 2016-08-15

						fileParseResult.addItem(parserMode);				
					}
					else {
						//处理每行数据，是否要判断一下为 "PMIG" 
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");	 //如果为空，会被过滤掉
						String c=parserMode.getChannelOrderNo();
						println "channelOrder=" +c;
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>12?record.get(12):null);
						parserMode.setDealDate(transDate);
						
						//add by nico.shao 
						parserMode.setAuthCode(record.get(8)); //退款订单，没有授权码
						parserMode.setTradeType("1");   //支付订单
						parserMode.setTransCurrency("CNY");
						parserMode.setSettlementCurrency("CNY");
						parserMode.setSettlementRate("1"); //
						//end 2016-08-15
						fileParseResult.addItem(parserMode);
					}
				}
				index++;
			}
			reader.close();

			//使用方法2，进行查找
			
			
			//注意：totalData 可能没有属性 
			if(totalData.size() >=5){
				//println "totalDatasize>=5  " + totalData.get(2) + "   "+ totalData.get(3) + "   " + totalData.get(4);

				totalDealAmount = new BigDecimal(totalData.get(2));
				totalTransFee = new BigDecimal(totalData.get(3));
				totalSettAmount = new BigDecimal(totalData.get(4));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileParseResult.setTotalDealAmount(totalDealAmount);
		fileParseResult.setTotalTransFee(totalTransFee);
		fileParseResult.setTotalSettAmount(totalSettAmount);
		fileParseResut.setCommand("pre");		//设置命令为预先

		println totalDealAmount;
		return fileParseResult;
	}

	//改变交易金额 
	/*
		List<String> stringList = new ArrayList<String>();
		stringList.add("6.31");
		stringList.add("-6.31");
		stringList.add(".232");
		stringList.add(".232-");
		stringList.add("6,323");
		stringList.add("6,323-");
		stringList.add("63232.2323");

		for(String str: stringList){
			String strResult=changeAmount(str);
			println str + "==>" +strResult;
		}
		
		return null;
	*/

	public String changeAmount(String amount){
		
		//(1)去掉 逗号
		String amount_1 = amount.replace(",","");
		
		//(2)如果 第一个符号是. 则在前面加 "0";
		if( (amount_1.length()>=1 )&& (amount_1.startsWith(".")) ){
			amount_1 = "0" + amount_1;
		}
		
		//(3) 如果是 - 数结尾，则去掉 负数, 并增加函数 
		if( (amount_1.endsWith("-"))||(amount_1.startsWith("-"))) {
			String amount_2 = amount_1.replace("-","");	
			amount_1 = "-" + amount_2;
		}

		return amount_1;
	}

}
