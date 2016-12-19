package com.pay.fundout.bankfile.generator.groovy

import org.apache.commons.lang.StringUtils
import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.psbc.PsbcGenModel

/**		
 *  @author lIWEI
 *  @Date 2011-8-11
 *  @Description 邮政 同行代发
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Psbc_G2P extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10010001".equals(bankCode);
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "0";
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.pay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		PsbcGenModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<PsbcGenModel> orderList = new ArrayList<PsbcGenModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new PsbcGenModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(genModel.getAmount(),new BigDecimal(1000));
			genModel.setAmount(amount);
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
			
		}
		ctxMap.put("orderList",orderList);
		
		String fileName ="psbc_"+dateString + "_"+fileGenerateResult.getBatchNum()+"_1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("psbc_gen2p.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		//ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10010001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}

}
