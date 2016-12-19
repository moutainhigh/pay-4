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
class Icbc_R2E extends AbstractBaseFileParser{
	
	/*
[BatchPayDetail]
查询时间: 20110525 15:20:02
批次号: HQF309076996
序号|币种|收款账号|收款单位名称|付款账号|付款单位名称|金额|用途|网上银行状态|用户备注|汇款方式|是否发送短信
HQF309076996-1|人民币|5861602011201100017507|汕头市澄海区万成丰工艺有限公司|2201027519200293325|海南新生信息技术有限公司|1.00元|货款|指令被拒绝授权| |加急|否
HQF309076996-2|人民币|97350154740003041|上海星颂实业有限公司|2201027519200293325|海南新生信息技术有限公司|2.00元|货款|指令被拒绝授权| |加急|否
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
			String bankAcct = trimAllWhitespace(_line[2]);
			String bankAcctName = trimAllWhitespace(_line[3]);
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
			
			/*if(feedBack.indexOf(',')==-1){
				fileParseResult.getErrorMsg().add("文件导入失败! txt文件内容第【 " + count + "】行【用户备注】 格式不合法，应为【*19位数字的流水号,其它*】，请确定文件内容后再做导入。");
			}else{
				String orderSeq = feedBack.substring(0,feedBack.indexOf(','));
				if(orderSeq.length() != 19 || !orderSeq.matches("[0-9]*")){
					fileParseResult.getErrorMsg().add("文件导入失败! txt文件内容第【 " + count + "】行【订单号】 格式不合法，应为数字并且最多为19个，请确定文件内容后再做导入。");
				}else{
					tempInfo.setOrderSeq(Long.valueOf(orderSeq));
				}
			}*/
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
