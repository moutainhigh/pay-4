/**
 *  <p>File: Sfz_G.groovy</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 hnapay.com . All rights reserved.海航新生版权所有</p>
 *	<p>Company: 海航新生</p>
 *  @author zengli
 *  @version 1.0  
 */
package com.hnapay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel;
import com.hnapay.fundout.bankfile.generator.model.sdb.SdbGeneratorModel

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-23
 * @see 
 */
class Sdb_G extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10016001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "ALL";
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		SdbGeneratorModel sdbGeneratorModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<SdbGeneratorModel> orderList = new ArrayList<SdbGeneratorModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			sdbGeneratorModel = new SdbGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, sdbGeneratorModel);
			//转账类型
			if(supportBank(sdbGeneratorModel.getBankKy())){//同行
				sdbGeneratorModel.setTransferType("1");
			}else if(2900 ==sdbGeneratorModel.getBankProvince() || 2900 == sdbGeneratorModel.getBankCity()){
			//同城
				sdbGeneratorModel.setTransferType("2");
			}else{
			//异地
				sdbGeneratorModel.setTransferType("3");
			}
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(sdbGeneratorModel.getAmount(),new BigDecimal(1000));
			sdbGeneratorModel.setAmount(amount);
			//用途
			sdbGeneratorModel.setUse(sdbGeneratorModel.getOrderSeq());
			
			orderList.add(sdbGeneratorModel);
			sumAmount = sumAmount.add(amount);
			
		}
		ctxMap.put("orderList",orderList);
		String fileName ="sdb_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"ALL";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("sdb_gen.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10016001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}	
}
