/**
 *  File: Hxb_G2E.groovy
 *  Description:
 *  Copyright 2006-2011 hnapay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-11-21   liwei     Create
 *
 */
package com.hnapay.fundout.bankfile.generator.groovy

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.hxb.HxbGenModel
import com.hnapay.poss.base.util.StringUtil

/**		
 *  @author lIWEI
 *  @Date 2011-11-21
 *  @Description 华夏 批量转账
 */
class Hxb_G2E extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10012001".equals(bankCode);
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
		HxbGenModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		protected DateFormat df5 = new SimpleDateFormat("yyyy-MM-dd");
		List<HxbGenModel> orderList = new ArrayList<HxbGenModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new HxbGenModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			//记录顺序号
			genModel.setSerialNo(String.valueOf(count));
			genModel.setDate(df5.format(new Date()));
			String remark = "";
			if(StringUtil.isEmpty(fileDetailMode.getOrderRemarks())) {
				remark = "往来款";
			}else{
				remark = fileDetailMode.getOrderRemarks();
			}
			genModel.setOrderRemarks(remark);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(genModel.getAmount(),new BigDecimal(1000));
			genModel.setAmount(amount);
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
			
		}
		ctxMap.put("orderList",orderList);
		
		String fileName ="hxb_"+dateString + "_"+fileGenerateResult.getBatchNum()+"_1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("hxb_gen2e.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		//ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10012001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}

}
