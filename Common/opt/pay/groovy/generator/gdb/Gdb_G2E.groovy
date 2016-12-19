package com.hnapay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils
import org.apache.commons.lang.StringUtils
import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.gdb.GdbGenModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-4
 *  @Description 广发银行 批量转账
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Gdb_G2E extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10015001".equals(bankCode);
	}

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
		GdbGenModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<GdbGenModel> orderList = new ArrayList<GdbGenModel>(size);
		def count = 0;
		Random random = new Random();//创建random对象
		String randomSr = String.valueOf(random.nextInt(1000000000));
		randomSr = StringUtils.leftPad(randomSr, 10, '0');
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new GdbGenModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			genModel.setDate(dateString);
			//金额
			BigDecimal amount = BnakFileUtil.divideBig(genModel.getAmount(),new BigDecimal(10));
			genModel.setAmount(amount);
			//用途
			genModel.setUse(genModel.getOrderSeq());
			genModel.setSerialNo(String.valueOf (count));
			if("0".equals(genModel.getTradeType().toString())){
				genModel.setTradeType(2);
			}else if("1".equals(genModel.getTradeType().toString())){
				genModel.setTradeType(1);
			}
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
			
		}
		ctxMap.put("orderList",orderList);
		String fileName ="GDBZFBB"+dateString + randomSr+"SR";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("gdb_gen2e.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10015001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}

}
