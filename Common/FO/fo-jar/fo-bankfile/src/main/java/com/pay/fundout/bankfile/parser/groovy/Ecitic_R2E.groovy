/**
 *  File: Ecitic_R2E.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-16   liwei     Create
 *
 */
package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.BusinessSupport
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-9-16
 *  @Description 中信结果文件
 */
class Ecitic_R2E extends AbstractBaseFileParser {

	/* 
查询结果下载											
支付方式	付方账号	付方账户名称	币种	收方账号	收方账户名称	金额	经办日期时间	交易状态	摘要	备注	经办人
中信内转账	7331710182600093709	GCPayment信息技术有限公司	人民币	7421810182600013710	西部航空有限责任公司	0.04	2011-09-14 15:24:59 	交易成功	摘要信息	备注信息	姜玥
中信内转账	7331710182600093709	GCPayment信息技术有限公司	人民币	7233210182600001506	天津航空有限责任公司	0.04	2011-09-14 15:24:59 	交易成功	摘要信息	备注信息	姜玥
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(4).toString().length()==0 || row.getCell(5).toString().length()==0 || row.getCell(6).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(4)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(5)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(6)));
			String status = trimAllWhitespace(String.valueOf(row.getCell(8)));
			tempInfo.setBankAcct(trimAllWhitespace(bankAcct));
			tempInfo.setBankAcctName(bankAcctName);
			
			//将金额的"逗号"去掉
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll(",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
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
