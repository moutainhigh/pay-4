/**
 *  File: Ceb_R2E.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   liwei     Create
 *
 */
package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BankFileUtil.*

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.BusinessSupport
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-8-22
 *  @Description 光大 批量转账 结果文件解析器
 */
class Ceb_R2E extends AbstractBaseFileParser {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.parser.AbstractBaseFileParser#parserFile(com.pay.fundout.bankfile.parser.helper.FileParseResult)
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		int count = 0;
		int columnCount = 0;
		WithdrawImportRecordModel tempInfo = null;
		String readLine;
		String statusFlag = "";
		while((readLine = reader.readLine()) != null) {
			count++;
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			if(readLine.contains("成功明细")){
				statusFlag = "成功";
			}
			if(readLine.contains("失败明细")){
				statusFlag = "失败";
			}
			if(!readLine.contains("|")){
				continue;	
			}
			
			String[] _line= readLine.split("\\|"); // 分隔
			tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(_line[3]);
			String bankAcctName = trimAllWhitespace(_line[4]);
			String amount = trimAllWhitespace(_line[5]);
			String status = statusFlag;
			
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
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			tempInfo.setOrderSeq(Long.valueOf(0));
			
			if (BusinessSupport.isBankStatusSuccess(status)) {
				tempInfo.setBankStatus(BANK_STATUS_1);
			} else if (BusinessSupport.isBankStatusFail(status)) {
				tempInfo.setBankStatus(BANK_STATUS_2);
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);

		}
	}

}
