/**
 *  File: Pdb_G2R.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2012-3-12   liwei     Create
 *
 */
package com.hnapay.fundout.bankfile.generator.groovy

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.refund.RefundGenModel

/**		
 *  @author lIWEI
 *  @Date 2012-3-12
 *  @Description 浦发充退生成器
 */
class Pdb_G2R extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10009001".equals(bankCode);
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "R";
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		Date now = new Date();
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0
		RefundGenModel model = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(now);
		
		List<RefundGenModel> orderList = new ArrayList<RefundGenModel>(size);
		def count = 0;
		DecimalFormat df = new DecimalFormat("00000000");
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				++count;
				model = new RefundGenModel();
				BeanUtils.copyProperties ( fileDetailMode, model);
				model.setRefundDate(df2.format(now));
				model.setRechargeDate(df3.format(fileDetailMode.getRechargeTime()));
				model.setRechargeBankOrder(fileDetailMode.getRechargeBankOrder().substring(7));
				amount = BnakFileUtil.divideNum(fileDetailMode.getApplyAmount(), new BigDecimal(1000));
				model.setApplyAmount(amount);
				model.setSerialNo(df.format(count));
				
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		ctxMap.put("orderList",orderList);
		String fileName = "pdb_"+dateString + "_" + fileGenerateResult.getBatchNum();
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("pdb_gen2r.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("10009001");
		ctxMap.put("summer",summaryModel);
		
		fileGenerateResult.setCtxMap(ctxMap);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		//构建批次信息
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}

}
