/**
 *  File: Ccb_C2E.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-19   liwei     Create
 *
 */
package com.hnapay.fundout.bankfile.parser.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*

import java.nio.charset.Charset

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.hnapay.poss.base.util.excel.CsvReader

/**
 *  @author lIWEI
 *  @Date 2011-08-19
 *  @Description 建行批量转账复核文件 .csv文件
 */
class Ccb_C2E extends AbstractBaseFileParser {

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser#parserFile(com.hnapay.fundout.bankfile.parser.helper.FileParseResult)
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		CsvReader csvReader = new CsvReader(fileParseResult.getTargetFile(),Charset.forName("GBK"));
		WithdrawImportRecordModel tempInfo = null;
		
		if (!csvReader.readHeaders()) {
			throw new Exception("文件格式不正确，请确定文件格式后再做导入。");
		}
		int count = 0;
		int columnCount = 0;
		while(csvReader.readRecord()) {
			count++;
			columnCount = csvReader.getColumnCount();
			/*if(count <= 3){
				csvReader.skipLine();
				continue;
			}*/
			
			if(columnCount != 9){
				csvReader.skipLine();
				continue;
			}
			
			String bankAcct = trimAllWhitespace(csvReader.get(1));
			String bankAcctName = trimAllWhitespace(csvReader.get(0));
			String amount = trimAllWhitespace(csvReader.get(5));
			//String feedBack = BnakFileStringUtil.trimAllWhitespace(csvReader.get(7));
		
			tempInfo = new WithdrawImportRecordModel();
			tempInfo.setBankAcct(trimAllWhitespace(bankAcct));
			tempInfo.setBankAcctName(trimAllWhitespace(bankAcctName));
			amount = trimAllWhitespace(amount);
			if(amount == null || amount.contains("金额")){
				csvReader.skipLine();
				continue;
			}
			if(amount.startsWith(".")){
				amount = "0"+amount;	
			}
			if(amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
			//String orderSeq =  trimAllWhitespace(feedBack);
			/*if(orderSeq.length() != 19 || !orderSeq.matches("[0-9]*")){
				fileParseResult.getErrorMsg().add("文件导入失败! Txt文件内容第【 " + i + "】行【订单号】 格式不合法，应为数字并且最多为19个，请确定文件内容后再做导入。");
			}*/
			//tempInfo.setOrderSeq(Long.valueOf(trimAllWhitespace(orderSeq)));
			tempInfo.setOrderSeq(Long.valueOf(0));
			
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
		}
	}
}
