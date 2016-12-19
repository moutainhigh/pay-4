/**
 *  File: Ceb_C2E.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   liwei     Create
 *
 */
package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-8-22
 *  @Description 光大 批量转账 复核文件解析器
 */
class Ceb_C2E extends AbstractBaseFileParser {

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
		while((readLine = reader.readLine()) != null) {
			count++;
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			if(!readLine.contains("|")){
				continue;
			}
			String[] _line= readLine.split("\\|"); // 分隔
			tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(_line[5]);
			String bankAcctName = trimAllWhitespace(_line[6]);
			String amount = trimAllWhitespace(_line[4]);
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
