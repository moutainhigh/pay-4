/**
 *  File: Sdb_G2R.groovy
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-15   liwei     Create
 *
 */
package com.pay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.refund.RefundGenModel

/**
 * 深发展充退生成文件
 */
class Sdb_G2R extends AbstractBankFileGenerator {

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10016001".equals(bankCode);
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
		Date now = new Date();
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0
		RefundGenModel model = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(now);
		
		List<RefundGenModel> orderList = new ArrayList<RefundGenModel>(size);
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				model = new RefundGenModel();
				BeanUtils.copyProperties ( fileDetailMode, model);
				amount = BnakFileUtil.divideNum(fileDetailMode.getApplyAmount(), new BigDecimal(1000));
				model.setApplyAmount(amount);
				
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		
		ctxMap.put("orderList",orderList);
		String fileName = "sdb_"+ dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"R1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xls");

		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("pingan_gen2r.xls");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeExcelFile(fileGenerateResult);
		summaryModel.setBankCode("10016001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}
	
	/*//原深发展
	public void buildBankDetailList_temp(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0
		RefundGenModel model = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		Random random = new Random();//创建random对象
		String randomNum = String.valueOf(random.nextInt(1000000));
		List<RefundGenModel> orderList = new ArrayList<RefundGenModel>(size);
		DecimalFormat df = new DecimalFormat("0000000000000");
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				model = new RefundGenModel();
				BeanUtils.copyProperties ( fileDetailMode, model);
				model.setRefundDate(df3.format(fileDetailMode.getApplyTimes()));
				model.setRechargeDate(df3.format(fileDetailMode.getRechargeTime()));
				amount = BnakFileUtil.divideBig(fileDetailMode.getApplyAmount(), new BigDecimal(10));
				model.setApplyAmount(amount);
				model.setAmountFormat(df.format(amount));
				model.setOrderSeqFormat(new StringBuffer(fileDetailMode.getRechargeBankOrder()).append("           ").toString());
				if(null != fileDetailMode.getDepositTypeName() && "BANK_B2B".equals(fileDetailMode.getDepositTypeName())){
					model.setChannelType("2");
				}else{
					model.setChannelType("1");
				}
				String remark = "";
				if(null != fileDetailMode.getApplyRemark()){
					remark = fileDetailMode.getApplyRemark();
				}
				
				int a = 0;
				for (int i = 0; i < remark.length(); i++) {
					String d  = String.valueOf(remark.charAt(i));
					if (d.matches("[\\u4E00-\\u9FA5]+")) {
						a += 2;
					}else {
						a +=1;
					}
				}
				
				StringBuffer b = new StringBuffer().append(remark);
				for(int i=a;i<100;i++){
					b.append(" ");
				}
				model.setApplyRemark(b.toString());
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		ctxMap.put("orderList",orderList);
		String batchNum = fileGenerateResult.getBatchNum();
		String fileName = "sdb_"+dateString + "_" + batchNum+"_R";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("sdb_gen2r.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("10016001");
		//ctxMap.put("summer",summaryModel);
		
		fileGenerateResult.setCtxMap(ctxMap);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		//构建批次信息
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}
	*/
}
