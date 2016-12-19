package com.hnapay.fundout.bankfile.parser.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.*

import com.hnapay.fundout.bankfile.parser.AbstractBaseFileParser
import com.hnapay.fundout.bankfile.parser.helper.FileParseResult
import com.hnapay.fundout.bankfile.parser.model.WithdrawImportRecordModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-1
 *  @Description 建行同行对私复核文件
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Ccb_C2P extends AbstractBaseFileParser {

/*
0000001|6227001218700480460|桂世勇|0.01|报销款|2001106221131468195|
0000002|6227001214620045384|李翠彩|0.01|报销款|2001106221131468198|

序号|付款账号|收款人姓名|收款账号|收款方开户机构|金额|摘要|备注1|备注2|
1|46001005036053003277|桂世勇|6227001218700480460||.01||||
2|46001005036053003277|李翠彩|6227001214620045384||.01||||

0000022|6227003171030192222|李方方|5026.00|CCB - Li Fang Fang 货款|2001112262238527007|
0000023|6227003172190244506|胡水源|5026.00|CCB - Hu Shui Yuan 货款|2001112262238527005|
0000024|6227003172060128730|仇红梅|5026.00|CCB - Chou Hong Mei 货款|2001112262238527004|
0000025|6227003234040696770|张喜|5026.00|CCB - Zhang Xi 货款|2001112262238527017|
		 */
	@Override
	protected void parserFile(FileParseResult fileParseResult) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileParseResult.getTargetFile(),"GBK"));
		WithdrawImportRecordModel tempInfo = null;
		String line;
		while((line = reader.readLine()) != null) {
			if(line == null || line.trim().length() == 0 ){
				continue;
			}
			if(!line.toString().contains("|")){
				continue;
			}
			line = line.toString().replaceAll("\\u007C{2}","| |");//连续的||间加空格
			def _line = line.tokenize("|") // 分隔
			
			def bankAcct = _line[1].toString().trim();
			def bankAcctName = _line[2].toString().trim();
			def amount = _line[3].toString().trim();
			def remark = _line[4].toString().trim();
			def orderSeq = _line[5].toString().trim();
			
			tempInfo = new WithdrawImportRecordModel();
			tempInfo.setBankAcct(trimAllWhitespace(bankAcct));
			tempInfo.setBankAcctName(trimAllWhitespace(bankAcctName));
			amount = trimAllWhitespace(amount);
			if(amount == null || amount.contains("金额")){
				continue;
			}
			if(amount.startsWith(".")){
				amount = "0"+amount;
			}
			//将金额的"逗号"去掉
			if(amount.contains("元")){
				amount = amount.substring(0, amount.length()-1);
			}
			if(amount != null && amount.contains(',')){
				amount = amount.replaceAll (",", "");
			}
			tempInfo.setBankAmount(new BigDecimal(trimAllWhitespace(amount)).multiply(new BigDecimal(1000)));
			if(null != orderSeq && !"".equals(orderSeq)){
				tempInfo.setOrderSeq(new Long(orderSeq));
			}
			tempInfo.setBankRemark (remark);
			tempInfo.setBankStatus(BANK_STATUS_3);
			tempInfo.setStatus(FILE_INFO_STATUS_DEFUALT);
			tempInfo.setCategory(1);
			fileParseResult.addDeailList(tempInfo);
			
		}
	}
}
