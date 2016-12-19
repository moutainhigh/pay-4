/**
 *  File: Abc_rc.groovy
 *  Description:
 *  Copyright 2015-2015 qiyun Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2015-10-21   chaoyue     Create
 *  2016-08-02   nico.shao   有的时候，没有退款订单号，也未必是ctv的
 */
package com.pay.fundout.bankfile.parser.groovy

import java.io.InputStreamReader
import java.io.BufferedReader

import java.io.FileInputStream

import com.pay.file.parser.AbstractBaseFileParser
import com.pay.file.parser.dto.FileParseResult
import com.pay.file.parser.dto.GatewayReconciliationParserMode
import com.pay.util.StringUtil
import com.pay.util.NumberUtil

import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.dto.CTVRefundOrderQueryModel;	//ctv RefundOrderQueryModel 
import com.pay.poss.dto.MIGSRefundOrderQueryModel;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**		
 *  @author chaoyue
 *  @Date 2015-10-24
 *  @Description 农行对账文件解析
 */
class Abc_rc extends AbstractBaseFileParser {


	@Override
	public FileParseResult parserFile(File file) throws Exception {

		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		GatewayOrderQueryService gatewayOrderQueryService = (GatewayOrderQueryService)wac.getBean("gatewayOrderQueryService");

		FileParseResult fileParseResult = new FileParseResult();		
		FileInputStream infile = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(infile,"GBK"));

		int count = 0;
		Integer  ctvRefundIndex = 0;
		def settlementRate = null;

		def tradeType = 0;
		def transCurrency = null;		//交易币种
		String readLine;
		while((readLine = reader.readLine()) != null) {
			count++;
			if(count <= 57){//对账记录从58行开始
				continue;
			}

			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}

			String[] _line= readLine.split("\\s{1,}"); // 分隔

			if(null == _line){
				continue;
			}

			if(readLine.indexOf("汇率") != -1){
				settlementRate = _line[_line.length-1];
			}

			def first = _line[0];
			
			if(readLine.indexOf("消费明细(人民币)") != -1){
				tradeType = 1;
			}
			if(readLine.indexOf("消费明细(DCC)") != -1){
				tradeType = 2;
				
				int indexOfBizhong =  readLine.indexOf("币种:");
				if(indexOfBizhong!=-1){	//后面取三个字段
					//println "before transcurrency";
					transCurrency = readLine.substring(indexOfBizhong+3,indexOfBizhong+6);	//是加3 么?
					//println "transCurrency :=" + transCurrency;
				}
			}
			
			if(readLine.indexOf("消费明细(多币种)") != -1){
				tradeType = 3;

				int indexOfBizhong =  readLine.indexOf("币种:");
				if(indexOfBizhong!=-1){	//后面取三个字段
					//println "before transcurrency";
					transCurrency = readLine.substring(indexOfBizhong+3,indexOfBizhong+6);	//是加3 么?
					//println "transCurrency :=" + transCurrency;
				}
			}
			
			if(readLine.indexOf("取消明细(多币种)") != -1){
				tradeType = 4;
			}
			if(readLine.indexOf("退货明细(多币种)") != -1){
				tradeType = 4;
			}
			if(readLine.indexOf("取消明细(人民币)") != -1){
				tradeType = 5;
			}
			if(readLine.indexOf("退货明细(人民币)") != -1){
				tradeType = 5;
			}
			
			if(readLine.indexOf("退货取消") != -1){
				tradeType = 6;
			}
			
			if(readLine.indexOf("消费拒付明细") != -1){
				tradeType = 7;
			}
			
			if(!StringUtil.isEmpty(first) && NumberUtil.isNumber(first)){
			
				GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();

				def dealDate = _line[2];
				def dealAmount = _line[3];
				def channelOrderNo = null;
				def settAmount = null;		//清算币种的清算净金额
				def transFee = null;
				def refeNumber = "";
				def settlDealAmount = null;	//清算币种的交易金额
				def settlementCurrency = "CNY";		//所有的清算都是人民币？ 

				//println "tradeType:" + tradeType + "," + readLine ;
				
				if(tradeType == 1 || tradeType == 0 ){//人民币交易
					channelOrderNo = _line[4];
					settAmount = _line[5];
					settlementRate = "1";				
					transFee = new BigDecimal(_line[3]).subtract(new BigDecimal(_line[5])).toString();
					
					transCurrency = "CNY";
					settlDealAmount = dealAmount;

					//CTV 订单编号 
					if((channelOrderNo != null) && (channelOrderNo.length()>21) && (_line.size()>=7)){					
						channelOrderNo = _line[6];	
						//println "ctv " + channelOrderNo;
					}

				}else if(tradeType == 2 || tradeType == 3 ){//外币交易
					channelOrderNo = _line[5];
					settAmount = _line[6];
					dealAmount = _line[4];  
					transFee = new BigDecimal(_line[3]).subtract(new BigDecimal(_line[6])).toString();
					
					settlDealAmount = _line[3];	

					//CTV 订单编号 
					if((channelOrderNo != null) && (channelOrderNo.length()>21) && (_line.size()>=8)){					
						channelOrderNo = _line[7];	
						//println "ctv 外币" + channelOrderNo;
					}
				}else if(tradeType == 5 ){//取消明细(人民币)
					channelOrderNo = _line[3];
					dealAmount = _line[2];
					dealDate = null;

					//也有可能是错误的
					println "--退款" + channelOrderNo ;
					if(channelOrderNo.length()<18) {  //应该是ctv的 
						//println "--ctv退款"  ;
						
						settAmount = _line[3];
						transFee = new BigDecimal(dealAmount).subtract(new BigDecimal(settAmount)).toString();

						//方法2 ，只是暂时先把数据加起来 
						CTVRefundOrderQueryModel  ctvROQMode = new CTVRefundOrderQueryModel();

						ctvROQMode.setOrgCode("10078001");
						ctvROQMode.setChannelOrderNo(_line[4]);
						ctvROQMode.setRefundRequestId(_line[5]);
						//ctvROQMode.setDealDate();
						//ctvROQMode.setDealTime();
						ctvROQMode.setCardNo(_line[1]);					
						ctvROQMode.setRefundAmount(dealAmount);
						ctvROQMode.setTransFee(transFee);	
						ctvROQMode.setSettAmount(settAmount);
						ctvROQMode.setQueryIndex(ctvRefundIndex.toString());
						
						//println ctvROQMMode.toString();

						ctvRefundIndex++;
						
						//ROQModeList2.add(migsROQMode);		// 如果调用方法2，就把这句注释掉，启用下面这句即可						
						queryRefundOrder_method1(gatewayOrderQueryService, ctvROQMode, parserMode,  fileParseResult);
						continue;		
					}

				}else if(tradeType == 4 ){//取消明细(多币种)
					channelOrderNo = _line[5];
					dealAmount = _line[4];
					dealDate = _line[2];
					
					//也有可能是错误的
					println "--退款" + channelOrderNo ;
					if(channelOrderNo.length()<18) {  //应该是ctv的 
						//println "--ctv退款"  ;
						
						settAmount = _line[5];
						transFee = new BigDecimal(dealAmount).subtract(new BigDecimal(settAmount)).toString();

						//方法2 ，只是暂时先把数据加起来 
						CTVRefundOrderQueryModel  ctvROQMode = new CTVRefundOrderQueryModel();

						ctvROQMode.setOrgCode("10078001");
						ctvROQMode.setChannelOrderNo(_line[6]);
						if (_line.size() > 7) {
						    ctvROQMode.setRefundRequestId(_line[7]);
						}
						//ctvROQMode.setDealDate();
						//ctvROQMode.setDealTime();
						ctvROQMode.setCardNo(_line[1]);					
						ctvROQMode.setRefundAmount(dealAmount);
						ctvROQMode.setTransFee(transFee);	
						ctvROQMode.setSettAmount(settAmount);
						ctvROQMode.setQueryIndex(ctvRefundIndex.toString());
						
						//println ctvROQMMode.toString();

						ctvRefundIndex++;
						
						//ROQModeList2.add(migsROQMode);		// 如果调用方法2，就把这句注释掉，启用下面这句即可						
						queryRefundOrder_method1(gatewayOrderQueryService, ctvROQMode, parserMode,  fileParseResult);
						continue;		
					}
				}
				
				//拒付的订单，就不要加进去了，反正channelOrderNo = null
				if(channelOrderNo != null)
				{
					parserMode.setChannelOrderNo(channelOrderNo);
					parserMode.setStatus("1");
					parserMode.setDealDate(dealDate);
					parserMode.setTransFee(transFee);
					parserMode.setDealAmount(dealAmount);
					parserMode.setSettlementRate(settlementRate);
					parserMode.setSettAmount(settAmount);
					parserMode.setRefeNumber(refeNumber);
					parserMode.setSettlementAmount(settlDealAmount);
					parserMode.setTransCurrency(transCurrency);
					parserMode.setSettlementCurrency("CNY");	//农行的清算币种，假设肯定是CNY 
					parserMode.setFixedFee("0");			//设置两种手续费	
					parserMode.setPerFee(transFee);	

					fileParseResult.addItem(parserMode);
				}
				//println "totalRecord" + fileParseResult.getTotalRecord();
			}
		}
		return fileParseResult;
	}

	//  这里是方法 1的调用 	
	public void queryRefundOrder_method1(GatewayOrderQueryService gatewayOrderQueryService, CTVRefundOrderQueryModel ctvROQMode,
			GatewayReconciliationParserMode parserMode,FileParseResult fileParseResult) {
	 
		
		Map paraMap = new HashMap();	
					
		List<CTVRefundOrderQueryModel> ctvROQModeList1 = new ArrayList<CTVRefundOrderQueryModel>();  		
						
		ctvROQModeList1.add(ctvROQMode);		
		paraMap.put("source","ctv");
		paraMap.put("method","1");		//方法1
		paraMap.put("ctvrefundorderlist",ctvROQModeList1);						

		Map resultMap = gatewayOrderQueryService.queryCTVRefundOrderByRequestId(paraMap);	//如果这句有问题，比如ordercetner没有启动，整个groovy 解析会失败
		
		println "gatewayOrderQueryService.queryCTVRefundOrderByRequestId 查询结果 : "+resultMap;
		List<Map> ctvResults = (List<Map>) resultMap.get("result");
		println "ctvResult : "+ ctvResults;
		if(ctvResults!=null && !ctvResults.isEmpty()){
			if(ctvResults.size()>1)
			{
				println "ctv_rc 通过参考号【 " + ctvROQMode.getChannelOrderNo() + "】查到多笔数据取第一条！==> " + ctvResults.get(0).get("refundOrderNo");
						fileParseResult.getErrorMsg().add("ctv_rc 通过参考号【 " + ctvROQMode.getChannelOrderNo()+ "】查到多笔数据取第一条！");
			}											
			parserMode.setChannelOrderNo(String.valueOf(ctvResults.get(0).get("refundOrderNo")));						
		}else{
			parserMode.setChannelOrderNo("106000000000000000");
		}
						
		parserMode.setStatus("1");
		parserMode.setTransFee(ctvROQMode.getTransFee());
		parserMode.setDealAmount(ctvROQMode.getRefundAmount());
		parserMode.setSettAmount(ctvROQMode.getSettAmount());
		parserMode.setRefeNumber(ctvROQMode.getChannelOrderNo());	//注意：这里是发送渠道订单编号
		parserMode.setDealDate(ctvROQMode.getDealDate());
		fileParseResult.addItem(parserMode);						
	}	
}
