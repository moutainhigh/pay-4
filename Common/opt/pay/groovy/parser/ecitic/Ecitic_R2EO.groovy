/**
 *  File: Ecitic_R2E.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-16   liwei     Create
 *
 */
package com.hnapay.fundout.bankfile.parser.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.BusinessSupport
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.hnapay.poss.base.util.StringUtil

/**		
 *  @author lIWEI
 *  @Date 2011-9-16
 *  @Description 中信结果文件 (跨行)
 */
class Ecitic_R2EO extends AbstractBaseFileParser {

	/* 
	查询结果下载
	支付方式	付方账号	付方账户名称	币种	收方账号	收方账户名称	金额	经办日期时间	交易状态	摘要	备注	经办人
	大额支付	7331710182600093709	海南新生信息技术有限公司	人民币	0202014210004720	海南新生信息技术有限公司	0.01	2011-12-23 10:27:09 	交易成功	测试		姜玥
	大额支付	7331710182600093709	海南新生信息技术有限公司	人民币	2201021519999612836	海南新生信息技术有限公司	0.01	2011-12-23 10:27:09 	交易成功	测试		姜玥
	大额支付	7331710182600093709	海南新生信息技术有限公司	人民币	6226090211515917	吕浩博	0.01	2011-12-23 10:27:09 	交易成功	测试		姜玥
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
			
			if(StringUtil.isEmpty(bankAcct) || StringUtil.isEmpty(bankAcctName) || StringUtil.isEmpty(amount)){
				continue;
			}
			
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
