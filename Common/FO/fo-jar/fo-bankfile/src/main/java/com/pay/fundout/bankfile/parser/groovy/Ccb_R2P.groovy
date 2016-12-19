package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.BusinessSupport
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-1
 *  @Description 建行同行对私结果文件
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Ccb_R2P extends AbstractBaseFileParser {

	/*
0000001|6227001218700480460|桂世勇|0.01|报销款|2001106221131468195|成功||
0000002|6227001214620045384|李翠彩|0.01|报销款|2001106221131468198|成功||

序号|收款账号|收款人姓名|金额|摘要|交易结果|失败原因|备注一|备注二|
1|6227001218700480460|桂世勇|.01||成功||||
		 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		WithdrawImportRecordModel tempInfo = null;
		String line;
		while((line = reader.readLine()) != null) {
			if(line == null || line.trim().length() == 0 ){
				continue;
			}
			if(!line.toString().contains("|")){
				continue;
			}
			line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
			def _line = line.tokenize("|") // 分隔
			
			def bankAcct = _line[1].toString().trim();
			def bankAcctName = _line[2].toString().trim();
			def amount = _line[3].toString().trim();
			def status = _line[5].toString().trim();
			
			tempInfo = new WithdrawImportRecordModel();
			tempInfo.setBankAcct(trimAllWhitespace(bankAcct));
			tempInfo.setBankAcctName(trimAllWhitespace(bankAcctName));
			amount = trimAllWhitespace(amount);
			if(amount == null || amount.contains("金额")){
				continue;
			}
			if(amount.startsWith(".")){
				amount = "0"+amount;
			}
			//将金额的"逗号"去掉
			if(amount.contains("元")){
				amount = amount.substring(0, amount.length()-1);
			}
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
			tempInfo.setOrderSeq(Long.valueOf(0));
			
			status = trimAllWhitespace(status);
			if (BusinessSupport.isBankStatusSuccess(status)) {
				tempInfo.setBankStatus(BANK_STATUS_1);
			} else if (BusinessSupport.isBankStatusFail(status)) {
				tempInfo.setBankStatus(BANK_STATUS_2);
				tempInfo.setBankFailureReason (_line[6]);
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
			
		}
	}
}
