package com.hnapay.fundout.bankfile.generator.groovy

import static com.hnapay.fundout.bankfile.common.util.BnakFileUtil.trimAllWhitespace

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.cib.CibGeneratorModel




/**
 * 
 * 兴业银行 (对公对私兼容)
 * @author liwei
 * @since 2013-12-17
 * @see
 */
class Cib_G extends AbstractBankFileGenerator{

	@Override
	public boolean supportBank(String bankCode) {
		return "10008001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "ALL";
	}

	public void buildBankDetailList(FileGenerateResult fileGenerateResult){
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		CibGeneratorModel genModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<CibGeneratorModel> orderList = new ArrayList<CibGeneratorModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			genModel = new CibGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, genModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(genModel.getAmount(),new BigDecimal(1000));
			genModel.setAmount(amount);
			
			//同城跨行支付，“汇入地址”不填写；异地跨行支付，“汇入地址”应填写。
			String bankCity = String.valueOf(genModel.getBankCity());
			if("2900".equals(bankCity)){//同城
				genModel.setCityName("");
			}else{
			}
			
			//是否兴业银行账户
			if(supportBank(genModel.getBankKy())){
				genModel.setIsSameBank("是");
			}else{
				genModel.setIsSameBank("否");
			}
			
			//用途说明
			genModel.setUse(trimAllWhitespace(genModel.getOrderSeq()));
			orderList.add(genModel);
			sumAmount = sumAmount.add(amount);
		}

		ctxMap.put("orderList",orderList);
		String fileName ="cib_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"ALL";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("cib_gen.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10008001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}
}
