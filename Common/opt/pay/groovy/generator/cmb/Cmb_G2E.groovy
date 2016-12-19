package com.hnapay.fundout.bankfile.generator.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.trimAllWhitespace
import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.trimWhitespace

import java.text.SimpleDateFormat
import java.util.Date

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.cmb.CmbGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-6-14
 *  @Description 招行银行文件生成器（对公对私兼容）
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
class Cmb_G2E extends AbstractBankFileGenerator {
	/* (non-Javadoc)
	 * @see com.hnapay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		return "10006001".equals(bankCode);
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
		CmbGeneratorModel cmbGeneratorModel = null; 
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<CmbGeneratorModel> orderList = new ArrayList<CmbGeneratorModel>(size);
		def count = 0;
		def amount = 0;
		for(FileDetailMode tempInfo : fileGenerateResult.getDetailList()){
				++count;
				cmbGeneratorModel = new CmbGeneratorModel(); 
				//BeanUtils.copyProperties ( fileDetailMode, cmbGenModel);
				cmbGeneratorModel.setBusiNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()+(count*1000l))));// 业务参考号
				cmbGeneratorModel.setPayeeAccNo(trimAllWhitespace(tempInfo.getBankAcct()));// 收款人账号
				cmbGeneratorModel.setPayeeAccName(trimWhitespace(tempInfo.getAccountName())); // 收款人名称
				cmbGeneratorModel.setPayeeBankBranch(trimWhitespace(tempInfo.getBankBranch())); // 收方开户支行
				cmbGeneratorModel.setPayeeProvince(trimWhitespace(tempInfo.getProvinceName())); // 收款人所在省
				cmbGeneratorModel.setPayeeCity(trimWhitespace(tempInfo.getCityName())); // 收款人所在市
				cmbGeneratorModel.setPayeeNo(trimAllWhitespace(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()+(count*5000l)))));// 收款人编号
				cmbGeneratorModel.setPayeeEmail("");// 收方邮件地址
				cmbGeneratorModel.setPayeeMobile("");// 收方移动电话
				cmbGeneratorModel.setCurrencyType("人民币");// 币种
				cmbGeneratorModel.setPayerBank("上海"); // 付款分行
				cmbGeneratorModel.setJsMode("快速"); // 结算方式
				cmbGeneratorModel.setPayerAccNo("121909444810801"); // 付方账号
				cmbGeneratorModel.setExpectDate(dateString);// 期望日
				cmbGeneratorModel.setExpectTime(""); // 期望时间
				amount = BnakFileUtil.divideNum(tempInfo.getAmount(), new BigDecimal(1000));
				cmbGeneratorModel.setAmount(String.valueOf(amount));// 金额
				if(0 == tempInfo.getTradeType() && amount >= 50000 ){
					cmbGeneratorModel.setUse(trimAllWhitespace(tempInfo.getOrderSeq())+",差旅费");//用途
				}else{
					cmbGeneratorModel.setUse(trimAllWhitespace(tempInfo.getOrderSeq())+","+tempInfo.getBatchNum()+"_dg");//用途
				}
				cmbGeneratorModel.setUnionBankCode(trimAllWhitespace(tempInfo.getUnionBankCode())); // 收方联行号
				cmbGeneratorModel.setPayeeBank(trimAllWhitespace(tempInfo.getBankName()));// 收方开户银行
				cmbGeneratorModel.setBusiSummary("");// 业务摘要
				sumAmount = sumAmount.add(amount);
				orderList.add(cmbGeneratorModel);
			}
		ctxMap.put("list",orderList);
		String fileName = "cmb_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_dg";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xlsx");
		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("cmb_gen2e.xlsx");
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(new BigDecimal(sumAmount));
		//生成模板文件
		writeExcelFile(fileGenerateResult);
		summaryModel.setBankCode("10006001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}

}
