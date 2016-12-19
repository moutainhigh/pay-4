package com.hnapay.fundout.bankfile.parser.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.BusinessSupport
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel
import com.hnapay.poss.base.exception.PossException
/**		
 *  @author lIWEI
 *  @Date 2011-6-1
 *  @Description 工商银行结果文件解析器 对公对私公用 文件格式为txt
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Icbc_R2S extends AbstractBaseFileParser{
	
	/*
[BatchPayDetail]
查询时间: 20110630 16:13:19
批次号: HEZ327561163
序号|币种|付款账号|付款单位名称|收款账号|收款单位名称|金额|用途|网上银行状态|银行反馈信息
HEZ327561163-1|RMB|2201021519999612836|海南新生信息技术有限公司|6222001001111482742|桂世勇|0.01元|报销|处理成功|全部成功
	 */
	@Override
	public void parserFile(FileParseResult fileParseResult) throws PossException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		int count = 0;
		int columnCount = 0;
		WithdrawImportRecordModel tempInfo = null;
		String readLine;
		while((readLine = reader.readLine()) != null) {
			count++;
			if(count <= 4){
				columnCount++;
				continue;
			}
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			String[] _line= readLine.split("\\|"); // 分隔
			tempInfo = new WithdrawImportRecordModel();
			String bankAcct = trimAllWhitespace(_line[4]);
			String bankAcctName = trimAllWhitespace(_line[5]);
			String amount = trimAllWhitespace(_line[6]);
			String feedBack = trimAllWhitespace(_line[9]);
			String status = trimAllWhitespace(_line[8]);
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			tempInfo.setBankRemark (feedBack);
			//将金额的"逗号"去掉
			if(amount.contains("元")){
				amount = amount.substring(0, amount.length()-1);
			}
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			
			tempInfo.setOrderSeq(Long.valueOf(0));
			
			if (BusinessSupport.isBankStatusSuccess(status)) {
				tempInfo.setBankStatus(BANK_STATUS_1);
			} else if (BusinessSupport.isBankStatusFail(status)) {
				tempInfo.setBankStatus(BANK_STATUS_2);
				tempInfo.setBankFailureReason (_line[9]);
			} else {
				tempInfo.setBankStatus(BANK_STATUS_3);
			}
			
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(2);
			fileParseResult.addDeailList(tempInfo);
		}
	}

}
