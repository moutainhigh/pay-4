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
import com.pay.poss.base.util.StringUtil

/**		
 *  @author lIWEI
 *  @Date 2011-8-12
 *  @Description 邮储批量转账结果文件
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Psbc_R2E extends AbstractBaseFileParser {

	/* 
批量转账查询结果									
收款人账号	  			 		金额		手续费	汇划费	用途		附言			交易时间		处理结果		失败原因
6221882900054472121		桂世勇 	0.01	0.5		5		拨款		123456789,	2011-07-05	交易成功	
6221882900002320695		张俊义 	0.01	0.5		5		拨款					2011-07-05	交易成功	
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		Workbook workbook = new XSSFWorkbook(fileParseResult.getTargetFile());       
		Sheet sheet = workbook.getSheetAt(0);
		WithdrawImportRecordModel tempInfo = null;
		for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if(row ==null || (row.getCell(0).toString().length()==0 || row.getCell(1).toString().length()==0 || row.getCell(2).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(0)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(1)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(2)));
			
			if(StringUtil.isEmpty(bankAcct) || StringUtil.isEmpty(bankAcctName) || StringUtil.isEmpty(amount)){
				continue;
			}
			
			String status = trimAllWhitespace(String.valueOf(row.getCell(8)));
			String orderSeq = trimAllWhitespace(String.valueOf(row.getCell(6))).replaceAll(",","");
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			
			if(orderSeq.length() != 19 || !orderSeq.matches("[0-9]*")){
				fileParseResult.getErrorMsg().add("文件导入失败! Excel文件内容第【 " + rowNum + "】行【订单号】 格式不合法，应为数字并且等于19位，请确定文件内容后再做导入。");
			}else{
				tempInfo.setOrderSeq(Long.valueOf(orderSeq));
			}
			if (BusinessSupport.isBankStatusSuccess(status)) {
				tempInfo.setBankStatus(BANK_STATUS_1);
			} else if (BusinessSupport.isBankStatusFail(status)) {
				tempInfo.setBankStatus(BANK_STATUS_2);
				tempInfo.setBankFailureReason(String.valueOf(row.getCell(9)));
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			//tempInfo.setBankRemark (String.valueOf(row.getCell(4)));
			//tempInfo.setBankBranch(String.valueOf(row.getCell(6)));
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
		}
	}

}
