/**
 *  File: Abc_G2E.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-10-20   liwei     Create
 *
 */
package com.hnapay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.abc.AbcGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-10-20
 *  @Description 农行对公
 */
class Abc_G2E extends AbstractBankFileGenerator {

	/* 
	 * 序号,转入账号,转入户名,交易金额,备注
	 * 1,03-301800040016693,海南新生信息技术有限公司,0.01,测试
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10002001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "1";
	}

	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		AbcGeneratorModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<AbcGeneratorModel> orderList = new ArrayList<AbcGeneratorModel>(size);
		def count = 0;
		DecimalFormat df = new DecimalFormat("0");
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new AbcGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			//记录顺序号
			genModel.setSerialNo(df.format(count));
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(genModel.getAmount(),new BigDecimal(1000));
			genModel.setAmount(amount);
			if(null==genModel.getOrderRemarks() || "".equals(genModel.getOrderRemarks())){
				genModel.setOrderRemarks("往来款");
			}
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
		}
		ctxMap.put("orderList",orderList);
		String fileName ="abc_${dateString}_${fileGenerateResult.getBatchNum()}_1";
		//String fileName = "abc_"+ dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("abc_gen2e.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10002001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}
}
