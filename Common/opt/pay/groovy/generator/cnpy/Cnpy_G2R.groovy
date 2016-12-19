package com.pay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BankFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.refund.RefundGenModel

/**
 *  @author lIWEI
 *  @Date 2011-7-28
 *  @Description 银联充退出款文件
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
class Cnpy_G2R extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "11001002".equals(bankCode);
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "R";
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.pay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0
		RefundGenModel model = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		Random random = new Random();//创建random对象
		String randomNum = String.valueOf(random.nextInt(1000000));
		List<RefundGenModel> orderList = new ArrayList<RefundGenModel>(size);
		def count = 0;
		DecimalFormat df = new DecimalFormat("0000");
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				++count;
				model = new RefundGenModel();
				BeanUtils.copyProperties ( fileDetailMode, model);
				model.setSerialNo(df.format(count));
				model.setRefundDate(df2.format(fileDetailMode.getApplyTimes()));
				model.setRechargeDate(df2.format(fileDetailMode.getRechargeTime()));
				amount = BankFileUtil.divideBig(fileDetailMode.getApplyAmount(), new BigDecimal(10));
				model.setApplyAmount(amount);
				model.setRechargeAmount(BankFileUtil.divideBig(fileDetailMode.getRechargeAmount(), new BigDecimal(10)));
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		ctxMap.put("orderList",orderList);
		String batchNum = fileGenerateResult.getBatchNum();
		String fileName = "TH104310048993766_"+dateString + "_" + "02";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("cnpy_gen2r.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("11001002");
		summaryModel.setDate(dateString);
		summaryModel.setBatchNum("02");
		summaryModel.setMerchantCode("104310048993766");
		summaryModel.setTotalCountString(df.format(size));
		ctxMap.put("summer",summaryModel);
		
		fileGenerateResult.setCtxMap(ctxMap);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		//构建批次信息
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}

}
