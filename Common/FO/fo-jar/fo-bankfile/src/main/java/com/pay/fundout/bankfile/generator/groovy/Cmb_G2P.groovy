/**
 *  <p>File: Cmb_G2P.groovy</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.GCPayment版权所有</p>
 *	<p>Company: GCPayment</p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.groovy

import java.awt.JobAttributes.MultipleDocumentHandlingType;

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.cmb.CmbGeneratorModel
import static com.pay.fundout.bankfile.common.util.BnakFileUtil.trimAllWhitespace
import static com.pay.fundout.bankfile.common.util.BnakFileUtil.trimWhitespace
/**
 * <p>招行对私文件</p>
 * @author zengli
 * @since 2011-6-27
 * @see 
 */
class Cmb_G2P extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10006001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "0";
	}

	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		CmbGeneratorModel cmbGeneratorModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<CmbGeneratorModel> orderList = new ArrayList<CmbGeneratorModel>(size);
		def count = 0;
		def amount = 0;
		for(FileDetailMode tempInfo : fileGenerateResult.getDetailList()){
			++count;
			cmbGeneratorModel = new CmbGeneratorModel();
			cmbGeneratorModel.setPayeeAccNo(trimAllWhitespace(tempInfo.getBankAcct()));// 收款人账号
			cmbGeneratorModel.setPayeeAccName(trimWhitespace(tempInfo.getAccountName())); // 收款人名称
			amount = BnakFileUtil.divideNum(tempInfo.getAmount(), new BigDecimal(1000));
			cmbGeneratorModel.setAmount(String.valueOf(amount));
			sumAmount = sumAmount.add(amount);
			orderList.add(cmbGeneratorModel);
		}
		ctxMap.put("list",orderList);
		String fileName = "cmb_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_ds";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xlsx");
		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("cmb_gen2p.xlsx");
		//生成模板文件
		writeExcelFile(fileGenerateResult);
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setBankCode("10006001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000));
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}

}
