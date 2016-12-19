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
 *  @author zengli
 *  @Date 2011-6-1
 *  @Description 交通银行复核文件解析器 对公对私公用 文件格式为excel
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Bcm_C2P extends AbstractBaseFileParser {
	/*
序号	户名	工号	卡号/折号	金额(元)	入账状态	失败原因	交易流水号
 0000001 	 唐文郁 	  	 6222600110035935886 	 0.01 	 未入账 	 	2001106231909468426
 0000002 	 李翠彩 	  	 6222600110033099636 	 0.01 	 未入账 	 	2001106232148468430

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
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			
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
