package com.hnapay.fundout.bankfile.parser.groovy

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.hnapay.poss.base.exception.PossException
import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*;

/**		
 *  @author lIWEI
 *  @Date 2011-6-1
 *  @Description 招商银行复核文件解析器 对公对私公用 文件格式为excel
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Cmb_C2P extends AbstractBaseFileParser {
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
				if(row ==null || (row.getCell(0).toString().length()==0 || row.getCell(1).toString().length()==0 
					|| row.getCell(2).toString().length()==0  )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(0)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(1)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(2)));
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setOrderSeq(Long.valueOf(0));
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			tempInfo.setBankRemark (String.valueOf(row.getCell(4)));
			tempInfo.setBankBranch(String.valueOf(row.getCell(6)));
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
		}
	}
	

}
