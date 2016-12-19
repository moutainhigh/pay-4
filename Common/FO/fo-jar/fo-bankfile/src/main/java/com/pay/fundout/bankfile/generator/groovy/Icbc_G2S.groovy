package com.pay.fundout.bankfile.generator.groovy
import java.util.Date
import java.util.List

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.icbc.IcbcGeneratorModel

/**
 * @author liwei
 * @since 2011-6-13
 * @see 工行上海出款文件
 */
class Icbc_G2P extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10001005".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "2";
	}
	
	public void buildBankDetailList(FileGenerateResult fileGenerateResult){
		
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		IcbcGeneratorModel icbcGenModel = null; 
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<IcbcGeneratorModel> orderList = new ArrayList<IcbcGeneratorModel>(size);
		def count = 0;
		BigDecimal amount = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				++count;
				icbcGenModel = new IcbcGeneratorModel(); 
				BeanUtils.copyProperties ( fileDetailMode, icbcGenModel);
				icbcGenModel.setMoneyType("RMB");
				icbcGenModel.setDate(dateString);
				icbcGenModel.setDetailKy("2");
				icbcGenModel.setBusiNo(String.valueOf(count));
				amount = BnakFileUtil.divideBig(fileDetailMode.getAmount(), new BigDecimal(10));
				icbcGenModel.setAmount(amount);
				if(null==icbcGenModel.getOrderRemarks() || "".equals(icbcGenModel.getOrderRemarks())){
					icbcGenModel.setOrderRemarks("劳务费");
				}
				orderList.add(icbcGenModel);
				sumAmount = sumAmount.add(amount);
			}
		ctxMap.put("orderList",orderList);
		String fileName = "icbc_"+ dateString + "_" +Integer.toHexString(fileGenerateResult.getBatchNum().hashCode()) +"_"+"s";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".bxt");
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("icbc_gen2s.vm");
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10001005");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}
}
