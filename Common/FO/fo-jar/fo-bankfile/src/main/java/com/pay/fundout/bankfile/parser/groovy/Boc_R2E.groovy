/**
 *  File: Boc_R2E.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-23   liwei     Create
 *
 */
package com.pay.fundout.bankfile.parser.groovy


import com.pay.fundout.bankfile.common.util.BankFileUtil

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.BusinessSupport
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.pay.util.StringUtil

/**		
 *  @author lIWEI
 *  @Date 2011-8-23
 *  @Description 中国银行 结果文件
 */
class Boc_R2E extends AbstractBaseFileParser {

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser#parserFile(com.hnapay.fundout.bankfile.parser.helper.FileParseResult)
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));//乱码解决
		int count = 0;
		String readLine = reader.readLine();
		while((readLine = reader.readLine()) != null) {
			count++;
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			String[] line= readLine.split("\\|"); // 分隔
			
			if(line.length==1){//最后一行 跳过
				continue;
			}
			tempInfo = new WithdrawImportRecordModel();
			String bankAcct = BankFileUtil.trimAllWhitespace(line[4]);
			String bankAcctName = BankFileUtil.trimAllWhitespace(line[8]);
			String amount = BankFileUtil.trimAllWhitespace(line[11]);
			//String status = BankFileUtil.trimAllWhitespace(String.valueOf(row.getCell(9)));						
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			
			if(StringUtil.isEmpty(bankAcct) || StringUtil.isEmpty(bankAcctName) || StringUtil.isEmpty(amount)){
				continue;
			}
			
			//将金额的"逗号"去掉
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			if(amount.startsWith(".")){
				amount = "0"+amount;
			}
			tempInfo.setBankAmount(new BigDecimal(BankFileUtil.trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
			tempInfo.setOrderSeq(Long.valueOf(0));
			
			tempInfo.setBankCode(BankFileUtil.trimAllWhitespace(line[20]));
			tempInfo.setBankName(BankFileUtil.trimAllWhitespace(line[7]));
			tempInfo.setBankStatus(BANK_STATUS_3);		
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);								
								
				
		}

	}

}
