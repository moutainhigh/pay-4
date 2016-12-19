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
import com.pay.poss.base.exception.PossException

/**		
 *  @author lIWEI
 *  @Date 2011-6-1
 *  @Description 招商银行结果文件解析器 对公对私公用 文件格式为excel
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Cmb_R2P extends AbstractBaseFileParser{

	/*
	C_EPTDAT：日期	M_DBTACC：付款方帐户（地区，帐号，币种）	C_TRSAMT：转账金额	M_CRTACC：收款方帐户（地区，帐号，币种）
	CRTNAM：收款方户名	C_STSFLG：处理标记	OPRDAT：操作日期（付款）	YURREF：未知。。。	NUSAGE：用途	DBTACC：付款方帐户
	CRTADR：收款方地址	CRTBNK：收款方银行	C_DBTREL：付款方开户名	C_STLCHN：未知。。。	DBTADR：付款方地址	DBTBNK：付款方银行
	DBTNAM：付款方名称	RTNFLG：返回标志
	*/
	@Override
	public void parserFile(FileParseResult fileParseResult) throws PossException {
		
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 6; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(0).toString().length()==0 || row.getCell(1).toString().length()==0 || row.getCell(2).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(0)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(1)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(2)));
			String status = trimAllWhitespace(String.valueOf(row.getCell(3)));
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			
			if (BusinessSupport.isBankStatusSuccess(status)) {
				tempInfo.setBankStatus(BANK_STATUS_1);
			} else if (BusinessSupport.isBankStatusFail(status)) {
				tempInfo.setBankStatus(BANK_STATUS_2);
				tempInfo.setBankFailureReason(String.valueOf(row.getCell(5)));
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			tempInfo.setOrderSeq(Long.valueOf(0));
			tempInfo.setBankRemark (String.valueOf(row.getCell(4)));
			tempInfo.setBankBranch(String.valueOf(row.getCell(6)));
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
				
		}
	}
}
