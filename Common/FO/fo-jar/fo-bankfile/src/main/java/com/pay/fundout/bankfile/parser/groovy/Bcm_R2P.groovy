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
 *  @Description 交行银行结果文件解析器 对公对私公用 文件格式为excel
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Bcm_R2P extends AbstractBaseFileParser{

	/*
序号	户名	工号	卡号/折号	金额(元)	入账状态	失败原因	交易流水号
 0000001 	 唐文郁 	  	 6222600110035935886 	 0.01 	 入账成功 	 	2001106231909468426
 0000002 	 李翠彩 	  	 6222600110033099636 	 0.01 	 入账成功 	 	2001106232148468430

	*/
	@Override
	public void parserFile(FileParseResult fileParseResult) throws PossException {
		
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(1).toString().length()==0 && row.getCell(3).toString().length()==0 && row.getCell(4).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(3)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(1)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(4)));
			String status = trimAllWhitespace(String.valueOf(row.getCell(5)));
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			tempInfo.setOrderSeq(Long.valueOf(0));
			if (BusinessSupport.isBankStatusSuccess(status)) {
				tempInfo.setBankStatus(BANK_STATUS_1);
			} else if (BusinessSupport.isBankStatusFail(status)) {
				tempInfo.setBankStatus(BANK_STATUS_2);
				tempInfo.setBankFailureReason(trimAllWhitespace(String.valueOf(row.getCell(6))));
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
		}
	}
}
