/**
 *  File: Boc_G2E.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-23   liwei     Create
 *
 */
package com.pay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.boc.BocGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-8-23
 *  @Description 中国银行 代发代扣（非上海地区）
 */
class Boc_G2P_NSH extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10003001".equals(bankCode);
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "6";
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.pay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		BocGeneratorModel bocModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<BocGeneratorModel> orderList = new ArrayList<BocGeneratorModel>(size);
		DecimalFormat df = new DecimalFormat("0000");
		def count = 0;
		for(fileDetailMode in fileGenerateResult.getDetailList()){
			bocModel = new BocGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, bocModel);
			++count;
			//金额
			def amount = BnakFileUtil.divideNum(bocModel.getAmount(),new BigDecimal(1000));
			bocModel.setAmount(amount);
			//客户业务编号
			bocModel.setCustBusiSerialNo(df.format(count));
			bocModel.setExpectDate(df3.format(bocModel.getCreateTime()));
			if("10003001".equals(fileDetailMode.getBankKy())){
				bocModel.setIsToBoc("1");
			}else {
				bocModel.setIsToBoc("0");
			}
			orderList.add(bocModel);
			sumAmount = sumAmount.add(amount);
		}

		ctxMap.put("orderList",orderList);
		String fileName ="boc_${dateString}_${fileGenerateResult.getBatchNum()}_6";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("boc_gen2p.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(df2.format(new Date())+"6");
		summaryModel.setDate2(dateString);
		summaryModel.setTotalCount(count);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setAreaCode(6);
		
		ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10003001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)

	}

}
