package com.hnapay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.ecitic.EciticGenModel

/**		
 *  @author lIWEI
 *  @Date 2011-9-16
 *  @Description 中信 批量转账(跨行) xls
 */
class Ecitic_G2EO extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10013001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "4";
	}

	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		EciticGenModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<EciticGenModel> orderList = new ArrayList<EciticGenModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new EciticGenModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			genModel.setPayerAccNo("7331710182600093709"); // 付方账号
			genModel.setMoneyType("人民币");// 币种
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(genModel.getAmount(),new BigDecimal(1000));
			genModel.setAmount(amount);
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
			
		}
		ctxMap.put("orderList",orderList);
		
		String fileName ="ecitic_"+dateString + "_"+fileGenerateResult.getBatchNum()+"_4";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xls");

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("ecitic_g2eo.xls");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		//生成模板文件
		writeExcelFile(fileGenerateResult);
		summaryModel.setBankCode("10013001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}

}
