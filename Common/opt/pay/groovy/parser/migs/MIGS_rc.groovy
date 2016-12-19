/**
 *  File: Credorax.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016-05-26  sch       for migs ��ʽ1��ǰ��10�У������������һ����¼
 *  2016-05-27  sch       ��migs ��ʽ1 �� migs ��ʽ2 ͳһ��һ���ļ����ˣ��ֱ�������������ʵ�� �����޸ĵĻ���ֻ��Ҫ�޸�����ļ����ɡ� 
 *			  �������кܶ��ظ��ĵط�����ʱ�����κ��Ż���
 *  2016-08-02  sch	  migs ��ʽ1���л�ҳ���ţ���Ҫȥ�������ҳ�� "1 "�� �ӹ����Ͻ��ǵ�55��110��165�еȡ���������һ���жϡ�  
 *  2016-08-15  sch       �޸�Ϊ�첽ģʽ�����Ұ��ļ��洢�ַ�����Ϊ GB2312 (utf-8 ��nico.shao �Ļ����� �Ƚ����ĳ����ˣ� 
 *  (1) ��ʽ˵��: 
 * 
 *		��ʽ		����	   ����		������	�˿�			������		�̻�����		����
 *		Migs 1		��****	  2016/05/23	PMIG		RMIG/����Ȩ��		14             �������⿨ MIGS ��      ǰ��10���ı���ÿ����������������ʾ
 *															
 *		Migs 2		ȫ������  20160408	PMIG		RMIG/����Ȩ��		13	       �����S�����⿨ MOTO ��  ǰ��8���ı���ÿ������1��
 *
 *		����Moto        ȫ������  20160406	PCEP 	        REFP/����Ȩ��		13	       �����S�����⿨ MOTO ��  ǰ��8���ı���ÿ������1��
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
 *  @Description Adyen ����ģ�������
 * 
 */
class MIGS_rc extends AbstractBaseFileParser {

	/*
	*  ����������ݵ�2�е��Ƿ� �� �����ϡ� �����������ж��� migs ��ʽ1�����Ǹ�ʽ2�����Ҹ��ݵ�7�л��ߵ�9���Ƿ�----\===== �������ж� 
	*/

	@Override
	public FileParseResult parserFile(File file) throws Exception {
		
		//for test
		int mode = 0;		
		
		//�ж��Ǹ�ʽ1�����Ǹ�ʽ2�������д���
		//�����жϵı�׼�� 
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GB2312");       
			BufferedReader reader = new BufferedReader(read);
			String readLine = reader.readLine();
			int index = 0;
			while((readLine = reader.readLine()) != null) {
				index++;
				if(index==1){
				    if(readLine.indexOf("�����̻� POS ����������ϸ��") != -1){
					 mode =1;
				    }
				    else if(readLine.indexOf(" �̻� POS ����������ϸ��") != -1){		//ǰ���һ���ո��ر�ָ��
					 mode =2;
				    }
				}
			
				//����һ���ж�
				if((index==6 ) && (mode >0)) {
					if( !(readLine.trim().startsWith("-----"))){
						println "�����б���Ϊ -----��ͷ";
						mode=0;	
					}
				}

				if(index==9){
					if(mode==1){
						if( !(readLine.trim().startsWith("======="))){
							println "mode 1 line9 ������===== ��ͷ��";
							mode=0;							
						}
					}else if(mode==2) {
						if((readLine.trim().startsWith("======="))){
							println "mode 2 line9 ������===== ��ͷ�ĵ���";
							mode=0;							
						}
					}
				}
 
				if(index>=9)
					break;		
			}
			reader.close();
			
			if(mode==1){
				return parserFile_Mode1(file);		//migs��ʽ1 
			}
			if(mode==2){
				return parserFile_Mode2(file);		//migs��ʽ2
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		FileParseResult fileParseResult = new FileParseResult();
		return fileParseResult;
	}


	//��������� Migs�Ĵ���1 ����ͷ10�У��м������еĸ�ʽ 
	public FileParseResult parserFile_Mode1(File file) throws Exception {
		println "mode1";

		FileParseResult fileParseResult = new FileParseResult();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		GatewayOrderQueryService gatewayOrderQueryService = (GatewayOrderQueryService)wac.getBean("gatewayOrderQueryService");

		
		BigDecimal totalDealAmount = 0;
		BigDecimal totalTransFee = 0;
		BigDecimal totalSettAmount = 0;
		
		int hasHeji=0;		//�Ѿ����ֺϼ� �����Ŀ��	
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GB2312");       
			BufferedReader reader = new BufferedReader(read);
			String readLine = reader.readLine();
			int index = 0;	//���������
			List<String> totalData = new ArrayList<String>();

			List<MIGSRefundOrderQueryModel> migsROQModeList2 = new ArrayList<MIGSRefundOrderQueryModel>();  //����2
			Integer  migsRefundIndex = 0;
			while((readLine = reader.readLine()) != null) {
				//println readLine;
				
				if(index>8){		//�ܹ���10�У�ȥ����һ�У����㿪ʼ���㣬��8�Ͷ���
					//println readLine;		
					
					//�����з�  2016-08-02
					if(readLine.startsWith("1 ")){
						//����ж�һ��index ��55��������
						readLine = readLine.substring(1,readLine.length());
					}
					//end 2016-08-02

					GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
					if(readLine.trim().startsWith("С��"))continue;
					if(readLine.trim().startsWith("-----"))continue;
					if(readLine.trim().startsWith("�ϼ�")) {
						hasHeji = 1;
						continue;
					}

					if( (hasHeji==1) && (readLine.trim().startsWith("PMIG"))){
						println "read pmig ===>break" ;
						String[] data = readLine.split(" ");
						for(String column : data){
							if(!StringUtil.isEmpty(column)){
								//��Ҫȥ�� ',' 
								String column_flit = column.replace(",",""); 
								totalData.add(column_flit);
							}
						}
						break;
					}
					
					//�Ѿ����ֺϼ��������Ͳ��ڼ��������ˣ��п���û��"PMIG�����Ŀ" �Ӷ���������������� 
					if(hasHeji==1) {
						println "�Ѿ��кϼ���";
						continue;
					}

					//�ٶ�һ�� add by sch 2016-05-26
					String readLine2 = reader.readLine();
					if(readLine2 == null){
						println "��ʽ����"
						break;
					}
					
					//�����з�  2016-08-02
					if(readLine.startsWith("1 ")){
						//����ж�һ��index ��55��������
						readLine = readLine.substring(1,readLine.length());
					}
					//end 2016-08-02

					//println readLine2 ;
					readLine = readLine + readLine2;  //���кϳ�һ��
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
						println "��������һ������ �ο��ŵı��� " + record.size() ;
						println readLine;

						break;
					}
					
					//println "step2";
					//����û����Ȩ�룬����RMIG ��ɵ�8��Ŀ�� 
					String dealAmount = changeAmount(record.get(5));
					String transFee   = changeAmount(record.get(6));
					String settAmount = changeAmount(record.get(7));
					String transDate = record.get(3).replace("/","");	//ͳһ��ʽΪ 20160513

					if( (record.get(9).trim().equals("RMIG")) || (record.get(8).trim().equals("RMIG")) ) {
						
						//println "��8��=[" + record.get(8) + "]";   //��ӡ���:  ��8��=[RMIG]	
						//������� �ο��ž��ǵ�11 ��Ŀ 
						
						int referenceNoIndex = 10;		
						if(record.size()<= referenceNoIndex ){
							continue;
						}
						
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");	 //���Ϊ�գ��ᱻ���˵�
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>referenceNoIndex?record.get(referenceNoIndex):null);
						parserMode.setDealDate(transDate);
						
						//parserMode.setAuthCode(record.get(8)); //�˿����û����Ȩ��
						parserMode.setTradeType("2");   //�˿��
						parserMode.setTransCurrency("CNY");
						parserMode.setSettlementCurrency("CNY");
						parserMode.setSettlementRate("1");

						fileParseResult.addItem(parserMode);
					}
					else 
					{						
						int referenceNoIndex = 11;		//Migs �汾 1 Ϊ 11��  Migs �汾2 Ϊ 12 
						if(record.size()<= referenceNoIndex ){
							continue;
						}

						
						//println "step3";
						
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");	 //���Ϊ�գ��ᱻ���˵�
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>referenceNoIndex?record.get(referenceNoIndex):null);
						parserMode.setDealDate(transDate);
						//add by nico.shao 2016-08-15
						parserMode.setAuthCode(record.get(8));
						parserMode.setTradeType("1");   //֧������
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

	//�����ʽ��������moto�ĸ�ʽ ,ÿ�������һ�� 
	//��ͷ��һ�� 
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
			int hasHeji=0;		//�Ѿ����ֺϼ� �����Ŀ��		
			List<String> totalData = new ArrayList<String>();

			List<MIGSRefundOrderQueryModel> migsROQModeList2 = new ArrayList<MIGSRefundOrderQueryModel>();  //����2
			Integer  migsRefundIndex = 0;

			while((readLine = reader.readLine()) != null) {
				if(index>6){
					//println readLine;

					GatewayReconciliationParserMode parserMode = new GatewayReconciliationParserMode();
					if(readLine.trim().startsWith("С��"))continue;
					if(readLine.trim().startsWith("-----")) continue;					
					if(readLine.trim().startsWith("�ϼ�")) {
						hasHeji = 1;
						continue;
					}
					if( (hasHeji==1) && (readLine.trim().startsWith("PMIG"))){
						println "read pmig ===>break" ;
						String[] data = readLine.split(" ");
						for(String column : data){
							if(!StringUtil.isEmpty(column)){
							   //��Ҫȥ�� ',' 
							   String column_flit = column.replace(",",""); 
							   totalData.add(column_flit);
							}
						}
						break;
					}
					
					//�Ѿ����ֺϼ��������Ͳ��ڼ��������ˣ��п���û��"PMIG�����Ŀ" �Ӷ���������������� 
					if(hasHeji==1) {
						println "�Ѿ��кϼ���";
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
						println "�����ֶ���Ŀ<12" ;
						continue;
					}
					
					//����û����Ȩ�룬����RMIG ��ɵ�8��Ŀ�� 
					
					String dealAmount = changeAmount(record.get(5));
					String transFee   = changeAmount(record.get(6));
					String settAmount = changeAmount(record.get(7));
					String transDate = record.get(3);	//ͳһ��ʽΪ 20160513

					if( (record.get(9).trim().equals("RMIG")) || (record.get(8).trim().equals("RMIG")) ) {
						
						//println "��8��=[" + record.get(8) + "]";   //��ӡ���:  ��8��=[RMIG]	
						//������� �ο��ž��ǵ�11 ��Ŀ 
						int referenceNoIndex = 11;		
						if(record.size()<= referenceNoIndex ){
							continue;
						}						
						
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");		//���Ϊ�գ��ᱻ���˵�
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>referenceNoIndex?record.get(referenceNoIndex):null);
						parserMode.setDealDate(transDate);
						//add by nico.shao 2016-08-15
						//parserMode.setAuthCode(record.get(8)); //�˿����û����Ȩ��
						parserMode.setTradeType("2");   //�˿��
						parserMode.setTransCurrency("CNY");
						parserMode.setSettlementCurrency("CNY");
						parserMode.setSettlementRate("1");

						//end nico.shao 2016-08-15

						fileParseResult.addItem(parserMode);				
					}
					else {
						//����ÿ�����ݣ��Ƿ�Ҫ�ж�һ��Ϊ "PMIG" 
						parserMode.setStatus("1");
						parserMode.setChannelOrderNo("1001234567890123456");	 //���Ϊ�գ��ᱻ���˵�
						String c=parserMode.getChannelOrderNo();
						println "channelOrder=" +c;
						parserMode.setTransFee(transFee);
						parserMode.setDealAmount(dealAmount);
						parserMode.setSettAmount(settAmount);
						parserMode.setRefeNumber(record.size()>12?record.get(12):null);
						parserMode.setDealDate(transDate);
						
						//add by nico.shao 
						parserMode.setAuthCode(record.get(8)); //�˿����û����Ȩ��
						parserMode.setTradeType("1");   //֧������
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

			//ʹ�÷���2�����в���
			
			
			//ע�⣺totalData ����û������ 
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
		fileParseResut.setCommand("pre");		//��������ΪԤ��

		println totalDealAmount;
		return fileParseResult;
	}

	//�ı佻�׽�� 
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
		
		//(1)ȥ�� ����
		String amount_1 = amount.replace(",","");
		
		//(2)��� ��һ��������. ����ǰ��� "0";
		if( (amount_1.length()>=1 )&& (amount_1.startsWith(".")) ){
			amount_1 = "0" + amount_1;
		}
		
		//(3) ����� - ����β����ȥ�� ����, �����Ӻ��� 
		if( (amount_1.endsWith("-"))||(amount_1.startsWith("-"))) {
			String amount_2 = amount_1.replace("-","");	
			amount_1 = "-" + amount_2;
		}

		return amount_1;
	}

}
