/**
 *  <p>File: Ccb_G2P.groovy</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.GCPayment版权所有</p>
 *	<p>Company: GCPayment</p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.ccb.CcbGeneratorModel

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-29
 * @see 
 */
class Ccb_G2P extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10004001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "0";
	}

	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		CcbGeneratorModel ccbModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<CcbGeneratorModel> orderList = new ArrayList<CcbGeneratorModel>(size);
		DecimalFormat df = new DecimalFormat("0000000");
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			ccbModel = new CcbGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, ccbModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(ccbModel.getAmount(),new BigDecimal(1000));
			ccbModel.setAmount(amount);
			//记录顺序号
			ccbModel.setSerialNo(df.format(count));
			orderList.add(ccbModel);
			sumAmount = sumAmount.add(amount);
		}

		ctxMap.put("orderList",orderList);
		String fileName ="ccb_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"ds";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("ccb_gen2p.vm");

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
