package com.pay.fundout.bankfile.parser.groovy

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import com.pay.fundout.bankfile.common.util.BankFileUtil
import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.BusinessSupport
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.pay.poss.base.exception.PossException

/**		
 *  @author mmzhang
 *  @Date 2016-4-25
 *  @Description 交行银行结果文件解析器 对公对私公用 文件格式为excel
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Cmbc_PR extends AbstractBaseFileParser{

	/*
制单类型	企业自制凭证号	客户号	预约标志	付款账号	交易金额	收款账号	收款人姓名	收款账户类型	子客户号	子付款账号	子付款账户名	子付款账户开户行名	用途	汇路	是否通知收款人	手机号码	邮箱	支付行号&支付行名称
2	001	10000003677	0		1.23	6013820800106302654	彭江波	0										40522&中国银行上海市浦东支行

	*/
	@Override
	public void parserFile(FileParseResult fileParseResult) throws PossException {
		
		Workbook workbook = new HSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 4; rowNum <= sheet.getLastRowNum(); rowNum++) {		
		
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(5).toString().length()==0 && row.getCell(6).toString().length()==0 && row.getCell(7).toString().length()==0 )){
				break;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = BankFileUtil.trimAllWhitespace(String.valueOf(row.getCell(6)));
			String bankAcctName = BankFileUtil.trimAllWhitespace(String.valueOf(row.getCell(7)));
			String amount = BankFileUtil.trimAllWhitespace(String.valueOf(row.getCell(5)));
		
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			String bankName = BankFileUtil.trimAllWhitespace(String.valueOf(row.getCell(18)));
			String[] split = bankName.split("&");
			tempInfo.setBankCode(split[0]);
			tempInfo.setBankName(split[1]);
			
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			tempInfo.setOrderSeq(Long.valueOf(0));			
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
		}
	}
}
