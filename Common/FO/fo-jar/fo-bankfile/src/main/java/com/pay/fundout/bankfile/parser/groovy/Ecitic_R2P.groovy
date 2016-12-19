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
 *  @Date 2011-9-16
 *  @Description 中信结果文件(同行代发)
 */
class Ecitic_R2P extends AbstractBaseFileParser {

	/* 
	文件类型:FTThirdGetcashQueryDetail
	标题:第三方支付-商户提现结果明细
	批次号|收款账号|收款账户名称|商户提现号|交易金额|备注|摘要|状态|失败原因
	-------------------------------------------------
	1|6226900205857325|张俊义|2001110131717483863|0.01|测试借记卡|123456789|交易成功|
	1|5182120008259835|马超湖|2001110131717483861|0.01|测试贷记卡|123456789|交易失败|帐号不存在
	 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		WithdrawImportRecordModel tempInfo = null;
		reader.eachLine() {line,i ->
			if(i > 4 && line.toString().trim().length() > 0){
				line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
				line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
				def _line = line.tokenize("|") // 分隔
				
				def status = trimAllWhitespace(_line[7]);
				def bankAcct = trimAllWhitespace(_line[1]);
				def bankAcctName = trimAllWhitespace(_line[2]);
				def orderSeq = trimAllWhitespace(_line[3]);
				def amount = trimAllWhitespace(_line[4]);
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
				tempInfo.setOrderSeq(new Long(orderSeq));
				status = trimAllWhitespace(status);
				
				if (BusinessSupport.isBankStatusSuccess(status)) {
					tempInfo.setBankStatus(BANK_STATUS_1);
				} else if (BusinessSupport.isBankStatusFail(status)) {
					tempInfo.setBankStatus(BANK_STATUS_2);
					tempInfo.setBankFailureReason (_line[8]);
				} else {
					tempInfo.setBankStatus(BANK_STATUS_3);
				}
				
				tempInfo.setBankRemark (feedBack);
				tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
				tempInfo.setCategory(2);
				fileParseResult.addDeailList(tempInfo);
			}
		}
	}

}
