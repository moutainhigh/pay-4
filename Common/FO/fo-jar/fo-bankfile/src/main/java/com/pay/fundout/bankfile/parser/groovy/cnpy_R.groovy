
package com.pay.fundout.bankfile.parser.groovy
import static com.pay.fundout.bankfile.common.util.BnakFileUtil.*

import java.io.BufferedReader
import java.io.InputStreamReader

import com.pay.fundout.bankfile.parser.AbstractBaseFileParser
import com.pay.fundout.bankfile.parser.helper.FileParseResult
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-5-31
 *  @Description 银联结果文件解析器 支持对公对私
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class cnpy_R extends AbstractBaseFileParser  {
	/*
	交易日期|商户号|商户流水|上传日期|上传流水|上传批次|CP日期|退单日期|状态|用户卡号|用户名|开户行|交易金额|手续费|支行名|支行号|省(自治区、直辖市)|市(地区、自治州)|交易目的|处理银行|批次文件名|批次内行号
	20101112|12345678|321478|20101111|123456|100002|20101110|20101112|4|12345678963|fighting|中国银联|10002.00|2.1|1|2|3|4|5|6|8|7
	20101112|12345678|321478|20101111|123456|100002|20101110|20101112|4|12345678963|fighting|中国银联|10003.00|2.2|1|2|3|4|5|6|8|7
	20101112|12345678|321478|20101111|123456|100002|20101110|20101112|4|12345678963|fighting|中国银联|10004.00|2.3|1|2|3|4|5|6|8|7
	关于状态
	<option value="0" >商户文件已上传</option>			
	<option value="1" >文件校验失败</option>
	<option value="2" >交易已接受</option>
	<option value="3" >财务已确认</option>
	<option value="4" >财务处理中</option>
	<option value="5" >已发送银行</option>
	<option value="6" >银行已退单</option>
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
			WithdrawImportRecordModel tempInfo = null;
			reader.eachLine() {line,i ->
				if(i > 1 && line.toString().trim().length() > 0){
					line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
					def _line = line.tokenize("|") // 分隔
					
					def status = _line[8].toString().trim();
					def bankAcct = _line[9].toString().trim();
					def bankAcctName = _line[10].toString().trim();
					def amount = _line[12].toString().trim();
					def feedBack = _line[18].toString().trim();
					
					tempInfo = new WithdrawImportRecordModel();
					tempInfo.setBankAcct(trimAllWhitespace(bankAcct));
					tempInfo.setBankAcctName(trimAllWhitespace(bankAcctName));
					if(amount != null && amount.contains(',')){
						amount = amount.replaceAll (",", "");
					}
					amount = trimAllWhitespace(amount);
					tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(10)));
					String orderSeq =  trimAllWhitespace(feedBack);
					if(orderSeq.length() != 19 || !orderSeq.matches("[0-9]*")){
						fileParseResult.getErrorMsg().add("文件导入失败! Txt文件内容第【 " + i + "】行【订单号】 格式不合法，应为数字并且最多为19个，请确定文件内容后再做导入。");
					}
					tempInfo.setOrderSeq(Long.valueOf(trimAllWhitespace(orderSeq)));
					
					status = trimAllWhitespace(status);
					if ("5".equals(status)) {
						tempInfo.setBankStatus(BANK_STATUS_1);
					} else if ("6".equals(status)) {
						tempInfo.setBankStatus(BANK_STATUS_2);
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
