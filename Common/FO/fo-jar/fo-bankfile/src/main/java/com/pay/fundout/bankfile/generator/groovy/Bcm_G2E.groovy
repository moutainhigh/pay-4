package com.pay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.bcm.BcmGeneratorModel

/**		
 *  @author lIWEI
 *  @Date 2011-7-13
 *  @Description 交行 批量转账
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 */
class Bcm_G2E extends AbstractBankFileGenerator{

	@Override
	public boolean supportBank(String bankCode) {
		return "10005001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "1";
	}

	public void buildBankDetailList(FileGenerateResult fileGenerateResult){

		def size = fileGenerateResult.getDetailList().size();
		def sumAmount = 0;
		BcmGeneratorModel bcmGenModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		def count = 0;
		List<BcmGeneratorModel> orderList = new ArrayList<BcmGeneratorModel>(size);
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			bcmGenModel = new BcmGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, bcmGenModel);
			//金额
			def amount = BnakFileUtil.divideBig(bcmGenModel.getAmount(),new BigDecimal(10));
			bcmGenModel.setAmount(amount);
			bcmGenModel.setSerialNo(String.valueOf(count));
			//币种
			bcmGenModel.setMoneyType("RMB");
			//是否交账号 交行='0',他行='1'
			if(supportBank(bcmGenModel.getBankKy())){
				bcmGenModel.setIsBcm("0");
			}else{
				bcmGenModel.setIsBcm("1");
			}
			//是否同城 同城='1',异地='0'
			String bankCity = String.valueOf(bcmGenModel.getBankCity());
			if("2900".equals(bankCity)){
				bcmGenModel.setWithTheCity("1");
			}else{
				bcmGenModel.setWithTheCity("0");
			}
			bcmGenModel.setBcmERPCode("");
			if(null==bcmGenModel.getOrderRemarks() || "".equals(bcmGenModel.getOrderRemarks())){
				bcmGenModel.setOrderRemarks("代发款项");
			}
			orderList.add(bcmGenModel);
			sumAmount += amount;
		}

		ctxMap.put("orderList",orderList);
		String fileName ="bcm_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"dg";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".plz");
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("bcm_gen2e.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		ctxMap.put("summer",summaryModel);
		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		
		summaryModel.setBankCode("10005001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}
}
