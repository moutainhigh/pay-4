package com.hnapay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.ccb.CcbGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-18
 *  @Description 建行 批量转账
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Ccb_G2E extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10004001".equals(bankCode);
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
		CcbGeneratorModel ccbModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<CcbGeneratorModel> orderList = new ArrayList<CcbGeneratorModel>(size);
		DecimalFormat df = new DecimalFormat("000");
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			ccbModel = new CcbGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, ccbModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(ccbModel.getAmount(),new BigDecimal(1000));
			ccbModel.setAmount(amount);
			//是否交他行
			if(supportBank(ccbModel.getBankKy())){
				ccbModel.setIsOtherBank("1");
			}else{
				ccbModel.setIsOtherBank("0");
				ccbModel.setUnionBankCode(" ");
			}
			//如果客户未提交实际用途，则统一为代发款项
			if(null==ccbModel.getOrderRemarks() || "".equals(ccbModel.getOrderRemarks())){
				ccbModel.setOrderRemarks("代发款项");
			}
			//记录顺序号
			ccbModel.setSerialNo(df.format(count));
			orderList.add(ccbModel);
			sumAmount = sumAmount.add(amount);
		}

		ctxMap.put("orderList",orderList);
		String fileName ="ccb_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_1_"+"ds";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("ccb_gen2e.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10004001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}

}
