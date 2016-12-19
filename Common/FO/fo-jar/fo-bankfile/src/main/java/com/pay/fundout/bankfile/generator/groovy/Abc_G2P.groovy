/**
 *  File: Abc_G2P.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-10-20   liwei     Create
 *
 */
package com.pay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.abc.AbcGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-10-20
 *  @Description
 */
class Abc_G2P extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10002001".equals(bankCode);
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
		AbcGeneratorModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<AbcGeneratorModel> orderList = new ArrayList<AbcGeneratorModel>(size);
		def count = 0;
		DecimalFormat df = new DecimalFormat("000");
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
		String fileName = "abc_"+ dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"0";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xls");

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("abc_gen2p.xls");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeExcelFile(fileGenerateResult);
		summaryModel.setBankCode("10002001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}

}
