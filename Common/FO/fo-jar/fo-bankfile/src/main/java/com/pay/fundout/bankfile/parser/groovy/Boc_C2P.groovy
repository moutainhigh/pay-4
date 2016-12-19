/**
 *  File: Boc_C2P.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-10-12   liwei     Create
 *
 */
package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-10-12
 *  @Description 中国银行 同行代发 复核文件
 */
class Boc_C2P extends AbstractBaseFileParser {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.parser.AbstractBaseFileParser#parserFile(com.pay.fundout.bankfile.parser.helper.FileParseResult)
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 10; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(5).toString().length()==0 || row.getCell(6).toString().length()==0 || row.getCell(4).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(5)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(6)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(4)));
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			
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
			if(amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
			tempInfo.setOrderSeq(Long.valueOf(0));
			
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
		}
	}
}
