package com.hnapay.fundout.bankfile.parser.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-9-16
 *  @Description 中信复核文件(同行代发) txt
 */
class Ecitic_C2P extends AbstractBaseFileParser {
	/*
	文件类型:FTThirdGetcashAuditDetail
	标题:第三方支付-商户提现审核明细
	批次号|收款账号|收款账户名称|商户提现号|交易金额|备注|摘要|状态
	-------------------------------------------------
	1|6226900205857325|张俊义|2001110131717483863|0.01|测试借记卡|123456789|未审核
	1|5182120008259835|马超湖|2001110131717483861|0.01|测试贷记卡|123456789|未审核
	*/
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		int count = 0;
		WithdrawImportRecordModel tempInfo = null;
		String readLine;
		while((readLine = reader.readLine()) != null) {
			count++;
			if(count <= 4){
				continue;
			}
			if(readLine == null || readLine.trim().length() == 0 ){
				continue;
			}
			String[] _line= readLine.split("\\|"); // 分隔
			tempInfo = new WithdrawImportRecordModel();
			
			String bankAcct = trimAllWhitespace(_line[1]);
			String bankAcctName = trimAllWhitespace(_line[2]);
			String orderSeq = trimAllWhitespace(_line[3]);
			String amount = trimAllWhitespace(_line[4]);
			String remark = trimAllWhitespace(_line[5]);
			
			tempInfo.setBankAcct(bankAcct);
			tempInfo.setBankAcctName(bankAcctName);
			tempInfo.setBankRemark (remark);
			//将金额的"逗号"去掉
			if(amount.contains("元")){
				amount = amount.substring(0, amount.length()-1);
			}
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(amount).multiply(new BigDecimal(1000)));
			tempInfo.setOrderSeq(new Long(orderSeq));
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(0);
			fileParseResult.addDeailList(tempInfo);
		}
	}

}
