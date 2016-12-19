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
 *  @Date 2011-7-13
 *  @Description 交行 批量转账 结果文件解析器
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Bcm_R2E extends AbstractBaseFileParser {

	/*
批次号	指令序号	付款账号	收款账号	收款户名	付款用途	交易金额	指令状态	企业业务编号	返回码	返回信息
10404088	04943463	310066179018170118503	03492300040010626	上海凝通电子商务有限公司	货款	0.01 	查询转账状态 	 	 SC0000	  人行支付系统处理中 
10404088	04943464	310066179018170118503	03492300040010626	上海凝通电子商务有限公司	货款	0.02 	查询转账状态 	 	 SC0000	  人行支付系统处理中 

	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(3).toString().length()==0 && row.getCell(4).toString().length()==0 && row.getCell(6).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(3)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(4)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(6)));
			String status = trimAllWhitespace(String.valueOf(row.getCell(7)));
			tempInfo.setOrderSeq(Long.valueOf(0));
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
				tempInfo.setBankFailureReason(trimAllWhitespace(String.valueOf(row.getCell(10))));
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
		}
	}

}
