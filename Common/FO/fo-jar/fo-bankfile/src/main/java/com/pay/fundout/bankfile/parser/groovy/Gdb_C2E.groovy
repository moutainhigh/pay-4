package com.pay.fundout.bankfile.parser.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*
import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.BusinessSupport
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-4
 *  @Description 广发批量转账复核文件
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Gdb_C2E extends AbstractBaseFileParser {

	/*
#总计信息
#注意事项说明：本文件中的金额均以分为单位！
#币种|日期|总计标志|总金额|总笔数|
RMB|20110701|1|3|2|
#明细指令信息
#币种|日期|明细标志|顺序号|付款账号开户行|付款账号|付款账号名称|收款账号开户行|收款账户银行名称|收款账号省份地市|收款账号地区码|收款账号|收款账号名称|收款账户公私标志|金额|汇款用途|备注信息|
RMB|20110701|2|1|广东发展银行杭州分行|134001516010021358|GCPayment信息技术有限公司|中国建设银行股份有限公司上海商城路支行|建设银行|上海市上海|105290061081|6227001214620045384|李翠彩|2|1|货款|123456789123456789|
RMB|20110701|2|2|广东发展银行杭州分行|134001516010021358|GCPayment信息技术有限公司|交通银行上海陆家嘴支行|交通银行|上海市上海|301290050828|6222600110033099636|李翠彩|2|2|货款|123456789123456789|
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		WithdrawImportRecordModel tempInfo = null;
		reader.eachLine() {line,i ->
			if(i > 6 && line.toString().trim().length() > 0){
				line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
				def _line = line.tokenize("|") // 分隔
				
				def bankAcct = trimAllWhitespace(_line[11]);
				def bankAcctName = trimAllWhitespace(_line[12]);
				def amount = trimAllWhitespace(_line[14]);
				def feedBack = trimAllWhitespace(_line[16]);
				
				tempInfo = new WithdrawImportRecordModel();
				tempInfo.setBankAcct(bankAcct);
				tempInfo.setBankAcctName(bankAcctName);
				if(amount != null && amount.contains(',')){
					amount = amount.replaceAll (",", "");
				}
				tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(10)));
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
				
				tempInfo.setBankStatus(BANK_STATUS_3);
				tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
				tempInfo.setCategory(1);
				fileParseResult.addDeailList(tempInfo);
			}
		}
	}

}
