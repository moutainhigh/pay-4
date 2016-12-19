package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.pay.poss.base.exception.PossException
/**
 *  @author lIWEI
 *  @Date 2011-6-1
 *  @Description 工商银行复核文件解析器 对公对私公用 文件格式为txt
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Icbc_C2S extends AbstractBaseFileParser{
	/*
[BatchPayDetail]
查询时间: 20110630 11:24:32
批次号: HEZ327561885
序号|币种|付款账号|付款单位名称|收款账号|收款单位名称|金额|用途|网上银行状态|银行反馈信息
HEZ327561885-1|RMB|2201021519999612836|GCPayment信息技术有限公司|6222001001111482742|桂世勇|0.01元|报销|等待银行处理|
		 */
	@Override
	public void parserFile(FileParseResult fileParseResult) throws PossException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		int count = 0;
		int columnCount = 0;
		WithdrawImportRecordModel tempInfo = null;
		String readLine;
		while((readLine = reader.readLine()) != null) {
			count++;
			if(count <= 4){
				columnCount++;
				continue;
			}
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			String[] _line= readLine.split("\\|"); // 分隔
			tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(_line[4]);
			String bankAcctName = trimAllWhitespace(_line[5]);
			String amount = trimAllWhitespace(_line[6]);
			//String feedBack = trimAllWhitespace(_line[9]);
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			//tempInfo.setBankRemark (feedBack);
			//将金额的"逗号"去掉
			if(amount.contains("元")){
				amount = amount.substring(0, amount.length()-1);
			}
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			
			tempInfo.setOrderSeq(Long.valueOf(0));
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
		}
	}
}
