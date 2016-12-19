package com.pay.fundout.bankfile.generator.groovy

import java.util.Date
import java.util.Random

import org.apache.commons.lang.StringUtils
import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.cnpy.CnpyGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-6-14
 *  @Description
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Cnpy_G extends AbstractBankFileGenerator {
	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "11001001".equals(bankCode);
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "ALL";
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.pay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0
		CnpyGeneratorModel cnpyGenModel = null; 
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		Random random = new Random();//创建random对象
		String cnpyBatchNum = String.valueOf(random.nextInt(1000000));
		cnpyBatchNum = StringUtils.leftPad(cnpyBatchNum, 6, '0');
		List<CnpyGeneratorModel> orderList = new ArrayList<CnpyGeneratorModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				++count;
				cnpyGenModel = new CnpyGeneratorModel(); 
				BeanUtils.copyProperties ( fileDetailMode, cnpyGenModel);
				cnpyGenModel.setDate(dateString);
				cnpyGenModel.setOrderSeq(fileDetailMode.getOrderSeq().substring(3));
				amount = BnakFileUtil.divideBig(fileDetailMode.getAmount(), new BigDecimal(10));
				cnpyGenModel.setAmount(amount);
				cnpyGenModel.setUse(fileDetailMode.getOrderSeq());
				sumAmount = sumAmount.add(amount);
				orderList.add(cnpyGenModel);
			}
		ctxMap.put("orderList",orderList);
		String fileName = "606060560200149" + "_" + dateString + "_" + cnpyBatchNum;
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".txt");
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("cnpy_gen.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(new BigDecimal(sumAmount));
		summaryModel.setBankCode("11001001");
		summaryModel.setCnpyBatchNum(cnpyBatchNum);
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		//构建批次信息
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}

}
