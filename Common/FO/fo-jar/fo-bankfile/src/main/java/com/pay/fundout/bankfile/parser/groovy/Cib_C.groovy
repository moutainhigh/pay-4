package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.pay.poss.base.util.StringUtil

/**	兴业银行复核文件	
 *  @author lIWEI
 *  @Date 2013-12-17
 *  @Description
 */
class Cib_C extends AbstractBaseFileParser {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.parser.AbstractBaseFileParser#parserFile(com.pay.fundout.bankfile.parser.helper.FileParseResult)
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		Workbook workbook=null;
		if(fileParseResult.getWorkName().equals(".xls")){//针对2003版本
			workbook = new HSSFWorkbook(fileParseResult.getTargetFile());
		}else{
			workbook = new XSSFWorkbook(fileParseResult.getTargetFile());
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(0).toString().length()==0 || row.getCell(1).toString().length()==0 || row.getCell(2).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(0)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(2)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(1)));
			if(StringUtil.isEmpty(bankAcct) || StringUtil.isEmpty(bankAcctName) || StringUtil.isEmpty(amount)){
				continue;
			}
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
