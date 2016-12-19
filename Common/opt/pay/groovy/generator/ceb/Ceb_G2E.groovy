/**
 *  File: Ceb_G2E.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   liwei     Create
 *
 */
package com.hnapay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.ceb.CebGenModel

/**
 * 光大批量转账
 */
class Ceb_G2E extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10014001".equals(bankCode);
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "1";
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		CebGenModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<CebGenModel> orderList = new ArrayList<CebGenModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new CebGenModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(genModel.getAmount(),new BigDecimal(1000));
			genModel.setAmount(amount);
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
			
		}
		ctxMap.put("orderList",orderList);
		
		String fileName ="ceb_"+dateString + "_"+fileGenerateResult.getBatchNum()+"_1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("ceb_gen2e.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		//ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10014001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}
}
