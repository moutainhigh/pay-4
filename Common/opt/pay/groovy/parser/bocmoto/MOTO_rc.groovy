/**
 *  File: Credorax.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   liwei     Create
 *  2016-05-26  sch       for migs 格式1，前面10行，后面的是两行一条记录
 *  2016-05-27  sch       把migs 格式1 和 migs 格式2 统一到一个文件中了，分别用两个函数来实现 这样修改的话，只需要修改这个文件即可。 
 *			  代码上有很多重复的地方，暂时不做任何优化。
 *  
 *  (1) 格式说明: 
 * 
 *		格式		卡号	   日期		正向交易	退款			总列数		商户名称		数据
 *		Migs 1		带****	  2016/05/23	PMIG		RMIG/无授权码		14             （线上外卡 MIGS ）      前面10行文本，每条数据由两行来表示
 *															
 *		Migs 2		全部卡号  20160408	PMIG		RMIG/无授权码		13	       （启赟线上外卡 MOTO ）  前面8行文本，每条数据1行
 *
 *		正常Moto        全部卡号  20160406	PCEP 	        REFP/有授权码		13	       （启赟线上外卡 MOTO ）  前面8行文本，每条数据1行
 *
 *  (2) 实现策略：我们使用了 方法1 和 方法2 两个不同的方法 向OrderCenter 进行提交
 *   方法1： 每次读取一条 退款订单，orderCenter 无法区分连续的退款订单.  这个可以用来简单测试。  
 *   方法2： 在所有的订单读取完毕之后，统一向orderCenter 进行提交，OrderCenter可以区分同一个网关订单的退款订单.   现在使用这个方法
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



	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		//for test
		int mode = 0;		
		
		//判断是格式1，还是格式2，还是有错误
		//我们判断的标准是 
		try {	
			return parserFile_Mode2(file);		// 格式2
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		FileParseResult fileParseResult = new FileParseResult();
		return fileParseResult;
	}


	
	//这个格式是类似于moto的格式 ,每条语句是一行 
	//开头是一行 
	public FileParseResult parserFile_Mode2(File file) throws Exception{
		
		println "mode2";

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

					if( record.get(9).trim().equals("REFP"))   {
						
						
						//下面假设 参考号就是第11 项目 
						int referenceNoIndex = 12;		
						if(record.size()<= referenceNoIndex ){
							continue;
						}
						
						//方法2 ，只是暂时先把数据加起来 
						MIGSRefundOrderQueryModel  migsROQMode = new MIGSRefundOrderQueryModel();

						migsROQMode.setOrgCode("10080001");
						migsROQMode.setRefeNumber(record.get(referenceNoIndex));
						migsROQMode.setDealDate(transDate);
						migsROQMode.setDealTime(record.get(4));
						migsROQMode.setCardNo(record.get(2));
						migsROQMode.setAuthorisation(record.get(8));
						migsROQMode.setRefundAmount(dealAmount);
						migsROQMode.setTransFee(transFee);	
						migsROQMode.setSettAmount(settAmount);
						migsROQMode.setQueryIndex(migsRefundIndex.toString());

						migsRefundIndex++;
						
						migsROQModeList2.add(migsROQMode);		//如果调用方法2，就把这句注释掉，启用下面这句即可						
						//queryRefundOrder_method1(gatewayOrderQueryService,  migsROQMode, parserMode,  fileParseResult);
						//continue;					
					}
					else {
						//处理每行数据，是否要判断一下为 "PCEP" 

						Map paraMap = new HashMap();					
						//根据卡号，交易金额，参考号查询
						def payAmount = record.get(5);		
				
						paraMap.put("authorisation",record.get(8));		
						paraMap.put("referenceNo",record.get(12));	

						Map resultMap = gatewayOrderQueryService.queryChannelOrder(paraMap);
						//println "gatewayOrderQueryService.queryChannelOrder查询结果 : "+resultMap;
						List<Map> channelOrders = (List<Map>) resultMap.get("result");
						println "channelOrders : "+channelOrders;
						if(channelOrders!=null && !channelOrders.isEmpty()){
							if(channelOrders.size()>1)
							{
								fileParseResult.getErrorMsg().add("moto_rc 通过参考号【 " + record.get(12)+ "】查到多笔数据取第一条！");
							}											
							parserMode.setChannelOrderNo(String.valueOf(channelOrders.get(0).get("channelOrderNo")));
						
						}else{
							parserMode.setChannelOrderNo("100000000");
						}					
						parserMode.setStatus("1");
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>12?record.get(12):null);
						parserMode.setDealDate(transDate);
						fileParseResult.addItem(parserMode);
					}
				}
				index++;
			}
			reader.close();

			//使用方法2，进行查找
			queryRefundOrder_methord2(gatewayOrderQueryService,migsROQModeList2,fileParseResult);
			
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

	/*
	* 查询退款订单 , 这里使用方法2 (注意，是方法2，不是格式2)
	* 输出参数：fileParseResult
	*/
	public void queryRefundOrder_methord2(GatewayOrderQueryService gatewayOrderQueryService,List<MIGSRefundOrderQueryModel> migsROQModeList,FileParseResult fileParseResult) {

		if(migsROQModeList == null || migsROQModeList.size()==0){
			return ;
		}

		Map paraMap = new HashMap();	
						
		//没有授权码,增加金额
		paraMap.put("source","migs");
		paraMap.put("method","2");		//方法2
		paraMap.put("migsrefundorderlist",migsROQModeList);						

		Map resultMap = gatewayOrderQueryService.queryMigsRefundOrderByRefernce(paraMap);	
		println "gatewayOrderQueryService.queryMigsRefundOrderByReference查询结果 : "+resultMap;
	
		List<Map> migsResults = (List<Map>) resultMap.get("result");
		println "migsResults : "+ migsResults;

		if( (migsResults!=null) && (!migsResults.isEmpty()) ){
			
			//针对每一条返回结果，我们增加对账单
			for( Map migsResult:migsResults){
			
				String refundOrderNo = migsResult.get("refundOrderNo");
				if((refundOrderNo == null )|| (refundOrderNo.length()==0)){
					continue;
				}

				GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();	

				parserMode.setChannelOrderNo(refundOrderNo);
				parserMode.setStatus("1");
				parserMode.setTransFee(migsResult.get("transFee"));
				parserMode.setDealAmount(migsResult.get("refundAmount"));
				parserMode.setSettAmount(migsResult.get("settAmount"));
				parserMode.setRefeNumber(migsResult.get("refeNumber"));
				parserMode.setDealDate(migsResult.get("dealDate"));
				fileParseResult.addItem(parserMode);
			}
		}else{
			println "gatewayOrderQueryService.queryMigsRefundOrderByReference查询结果 ,没有数据";
		}		
	}
}
