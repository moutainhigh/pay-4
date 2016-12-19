package com.pay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.bcm.BcmGeneratorModel

/**
 * 
 * <p>交行对私</p>
 * @author wucan
 * @since 2011-6-15
 * @see
 */
class Bcm_G2P extends AbstractBankFileGenerator{


	@Override
	public boolean supportBank(String bankCode) {
		return "10005001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "0";
	}

	public void buildBankDetailList(FileGenerateResult fileGenerateResult){

		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		BcmGeneratorModel bcmGenModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<BcmGeneratorModel> orderList = new ArrayList<BcmGeneratorModel>(size);
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			bcmGenModel = new BcmGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, bcmGenModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(bcmGenModel.getAmount(),new BigDecimal(1000));
			bcmGenModel.setAmount(amount);
			orderList.add(bcmGenModel);
			sumAmount = sumAmount.add(amount);
		}
		ctxMap.put("orderList",orderList);
		String fileName = "bcm_"+ dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"ds";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xls");

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("bcm_gen2p.xls");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeExcelFile(fileGenerateResult);
		summaryModel.setBankCode("10005001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}
	/*public void buildBankDetailList(FileGenerateResult fileGenerateResult){
		
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		BcmGeneratorModel bcmGenModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<BcmGeneratorModel> orderList = new ArrayList<BcmGeneratorModel>(size);
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			bcmGenModel = new BcmGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, bcmGenModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(bcmGenModel.getAmount(),new BigDecimal(1000));
			bcmGenModel.setAmount(amount);
			orderList.add(bcmGenModel);
			sumAmount = sumAmount.add(amount);
		}
		
		ctxMap.put("orderList",orderList);
		String fileName = "bcm_"+ dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"ds";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		
		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("bcm_gen2p.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10005001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}*/
}
