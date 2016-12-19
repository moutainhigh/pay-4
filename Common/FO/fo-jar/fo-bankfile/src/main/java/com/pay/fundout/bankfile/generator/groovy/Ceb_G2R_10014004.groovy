package com.pay.fundout.bankfile.generator.groovy

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.refund.RefundGenModel

/**
 *  @author terry_ma
 *  @Date 2011-7-29
 *  @Description 光大银行充退解析器 txt
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Ceb_G2R_10014004 extends AbstractBankFileGenerator{
	
/*
退货总金额
退货总笔数
流水号（订单号）、交易日期、协议号、退货金额、交易货币代码、原流水号（订单号）、原交易日期、摘要

注：协议号只有协议支付才有，普通支付没有协议号。
*/
	
	/* (non-Javadoc)
	* @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	*/
   @Override
   public boolean supportBank(String bankCode) {
	   return "10014004".equals(bankCode);
   }

   /* (non-Javadoc)
	* @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	*/
   @Override
   public String supportTradeType() {
	   return "R";
   }
	
	
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
		DecimalFormat df = new DecimalFormat("000000");
		DateFormat df4 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		DateFormat df5 = new SimpleDateFormat("HHmmss");
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				++count;
				model = new RefundGenModel();
				BeanUtils.copyProperties ( fileDetailMode, model);
				model.setRefundDate(df4.format(now));
				model.setRechargeDate(df3.format(fileDetailMode.getRechargeTime()));
				amount = BnakFileUtil.divideNum(fileDetailMode.getApplyAmount(), new BigDecimal(1000));
				model.setApplyAmount(amount);
				//model.setApplyRemark("["+fileDetailMode.getDetailKy()+"]"+fileDetailMode.getApplyRemark());
				model.setSerialNo(df.format(Integer.valueOf(df5.format(now))+count));
				
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		ctxMap.put("orderList",orderList);
		String fileName = "ceb_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_4";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("ceb_gen2r.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("10014004");
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
