package com.pay.fundout.bankfile.generator.groovy

import static com.pay.fundout.bankfile.common.util.BnakFileUtil.trimAllWhitespace

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.abc.AbcGeneratorModel

/**
 * 
 * <p>农业银行 (对公对私兼容)</p>
 * @author wucan
 * @since 2011-6-15
 * @see
 */
class Abc_G extends AbstractBankFileGenerator{

	@Override
	public boolean supportBank(String bankCode) {
		return "10002001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "ALL";
	}

	public void buildBankDetailList(FileGenerateResult fileGenerateResult){

		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount = 0;
		AbcGeneratorModel abcModel = null;
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		List<AbcGeneratorModel> orderList = new ArrayList<AbcGeneratorModel>(size);
		DecimalFormat df = new DecimalFormat("0000");
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
			++count;
			abcModel = new AbcGeneratorModel();
			BeanUtils.copyProperties (fileDetailMode, abcModel);
			//金额
			BigDecimal amount = BnakFileUtil.divideNum(abcModel.getAmount(),new BigDecimal(1000));
			abcModel.setAmount(amount);
			//记录顺序号
			abcModel.setSerialNo(df.format(count));
			//结算种类
			String bankCity = String.valueOf(abcModel.getBankCity());
			if("2900".equals(bankCity)){
				abcModel.setPayType("2");
				abcModel.setTradeType(0);
			}else{
				abcModel.setPayType("3");
				if(supportBank(abcModel.getBankKy())){
					abcModel.setTradeType(0);
				}else{
					abcModel.setTradeType(1);
				}
				
			}
			//钞汇种类
			abcModel.setCurrFlag();
			//用途说明
			abcModel.setUse(trimAllWhitespace(abcModel.getOrderSeq()));
			orderList.add(abcModel);
			sumAmount = sumAmount.add(amount);
		}

		ctxMap.put("orderList",orderList);
		String fileName ="abc_"+dateString + "_" + fileGenerateResult.getBatchNum()+"_"+"ALL";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);

		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setCtxMap(ctxMap);
		fileGenerateResult.setTemplateName("abc_gen.vm");

		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setDate(dateString);
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);

		//生成模板文件
		writeVelocityFile(fileGenerateResult);
		summaryModel.setBankCode("10002001");
		summaryModel.setBatchNum(fileGenerateResult.getBatchNum());
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		//构建批次信息
		buildBatchFileInfo(fileGenerateResult, summaryModel)
	}
}
