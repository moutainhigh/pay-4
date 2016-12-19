package com.hnapay.fundout.bankfile.parser.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.hnapay.poss.base.exception.PossException
import com.hnapay.poss.base.util.StringUtil

/**		
 *  @author lIWEI
 *  @Date 2011-6-1
 *  @Description 招商银行复核文件解析器 对公对私公用 文件格式为excel
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Cmb_C2E extends AbstractBaseFileParser {
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
			if(row ==null || (row.getCell(2).toString().length()==0 || row.getCell(3).toString().length()==0 || row.getCell(4).toString().length()==0 )){
				continue;
			}
		    tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(String.valueOf(row.getCell(3)));
			String bankAcctName = trimAllWhitespace(String.valueOf(row.getCell(4)));
			String amount = trimAllWhitespace(String.valueOf(row.getCell(2)));
			if(StringUtil.isEmpty(bankAcct) || StringUtil.isEmpty(bankAcctName) || StringUtil.isEmpty(amount)){
				continue;
			}
			String feedBack = trimAllWhitespace(String.valueOf(row.getCell(8)));
			tempInfo.setBankRemark(feedBack);
			if(bankAcct.indexOf(',')==-1){
				fileParseResult.getErrorMsg().add("文件导入失败! Excel文件内容第【 " + rowNum + "】行【收款方信息】 格式不合法，应为【地区,账号,币种】，请确定文件内容后再做导入。");
			}
			
			def bankAccts = bankAcct.tokenize(",");
			if(bankAccts.size() == 3){
				bankAcct = bankAccts[1];
			}else{
				bankAcct = bankAccts[0];
			}
			
			tempInfo.setBankAcct(bankAcct);
			
			tempInfo.setBankAcctName(bankAcctName);
			
			//将金额的"逗号"去掉
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
			
			if(feedBack.indexOf(',')==-1){
				fileParseResult.getErrorMsg().add("文件导入失败! Excel文件内容第【 " + rowNum + "】行【银行反馈信息】 格式不合法，应为【*19位数字的流水号,其它*】，请确定文件内容后再做导入。");
			}else{
				String orderSeq = feedBack.substring(0,feedBack.indexOf(','));
				orderSeq = trimAllWhitespace(orderSeq);
				if(orderSeq.length() != 19 || !orderSeq.matches("[0-9]*")){
					fileParseResult.getErrorMsg().add("文件导入失败! Excel文件内容第【 " + rowNum + "】行【订单号】 格式不合法，应为数字并且最多为19个，请确定文件内容后再做导入。");
				}else{
					tempInfo.setOrderSeq(Long.valueOf(orderSeq));
				}
			}
			tempInfo.setBankBranch(String.valueOf(row.getCell(11)));
			
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
				
		}
	}
	

}
