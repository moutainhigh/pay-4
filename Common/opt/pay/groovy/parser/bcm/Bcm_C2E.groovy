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

/**		
 *  @author lIWEI
 *  @Date 2011-7-13
 *  @Description 交行 批量转账 复核文件解析器
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Bcm_C2E extends AbstractBaseFileParser {

	/*
客户名称：			海南新生信息技术有限公司			批次号：	10404088		
批准	拒绝	明细	指令序号	指令状态 	汇款金额（元）	付款账号	收款账号	收款单位	收款人开户行
		查看	04943463	未授权	0.01	 310066179018170118503	 03492300040010626	上海凝通电子商务有限公司	农业银行卢湾支行
		查看	04943464	未授权	0.02	 310066179018170118503	 03492300040010626	上海凝通电子商务有限公司	农业银行卢湾支行

	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(3).toString().length()==0 && row.getCell(4).toString().length()==0 && row.getCell(6).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(7)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(8)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(5)));
			tempInfo.setOrderSeq(Long.valueOf(0));
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
		}
	}
}
