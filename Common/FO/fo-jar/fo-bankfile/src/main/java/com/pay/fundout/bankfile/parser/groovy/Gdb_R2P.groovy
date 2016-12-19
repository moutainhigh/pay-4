package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-4
 *  @Description 广发同行代发结果文件
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Gdb_R2P extends AbstractBaseFileParser {

	/*
#[BatchPayDetail]
#查询时间：20110701 13:26:38
#批次号：10021264
#银行的代理流水号：10021264
#序号|收款账号|收款账号单位名称|金额|网上银行状态|用户备注|银行反馈信息|
1|6225683523000519571|王冠轩|0.01元|0|123456789||
2|6225683523000519571|王冠轩|0.02元|0|123456789||
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		WithdrawImportRecordModel tempInfo = null;
		reader.eachLine() {line,i ->
			if(i > 5 && line.toString().trim().length() > 0){
				line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
				def _line = line.tokenize("|") // 分隔
				
				def status = trimAllWhitespace(_line[4]);
				def bankAcct = trimAllWhitespace(_line[1]);
				def bankAcctName = trimAllWhitespace(_line[2]);
				def amount = trimAllWhitespace(_line[3]);
				def feedBack = trimAllWhitespace(_line[5]);
				
				tempInfo = new WithdrawImportRecordModel();
				tempInfo.setBankAcct(bankAcct);
				tempInfo.setBankAcctName(bankAcctName);
				if(amount.contains("元")){
					amount = amount.substring(0, amount.length()-1);
				}
				if(amount != null && amount.contains(',')){
					amount = amount.replaceAll (",", "");
				}
				tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
				if(feedBack.indexOf(',')==-1){
					fileParseResult.getErrorMsg().add("文件导入失败! txt文件内容第【 " + i + "】行【银行反馈信息】 格式不合法，应为【*19位数字的流水号,其它*】，请确定文件内容后再做导入。");
				}else{
					String orderSeq = feedBack.substring(0,feedBack.indexOf(','));
					if(orderSeq.length() != 19 || !orderSeq.matches("[0-9]*")){
						fileParseResult.getErrorMsg().add("文件导入失败! txt文件内容第【 " + i + "】行【订单号】 格式不合法，应为数字并且最多为19个，请确定文件内容后再做导入。");
					}else{
						tempInfo.setOrderSeq(Long.valueOf(orderSeq));
					}
				}
				status = trimAllWhitespace(status);
				if ("0".equals(status)) {
					tempInfo.setBankStatus(BANK_STATUS_1);
				} else if ("1".equals(status)) {
					tempInfo.setBankStatus(BANK_STATUS_2);
					tempInfo.setBankFailureReason (_line[6]);
				} else {
					tempInfo.setBankStatus(BANK_STATUS_3);
				}
				tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
				tempInfo.setCategory(2);
				fileParseResult.addDeailList(tempInfo);
			}
		}
	}
}
