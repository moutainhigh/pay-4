package com.pay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BnakFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.refund.RefundGenModel

/**		
 *  @author wucan
 *  @Date 2013-05-22
 *  @Description 兴业银行充退出款文件
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. GCPayment版权所有
 *  商户号|订单号|订单金额|退款金额|备注
 */
class Abc_G2R extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10008001".equals(bankCode);
	}

	@Override
	public String supportTradeType() {
		return "R";
	}

	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0;
		BigDecimal rechargeAmount = 0;
		RefundGenModel model = null; 
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		Random random = new Random();//创建random对象
		String randomNum = String.valueOf(random.nextInt(1000000));
		List<RefundGenModel> orderList = new ArrayList<RefundGenModel>(size);
		def count = 0;
		for(FileDetailMode fileDetailMode : fileGenerateResult.getDetailList()){
				++count;
				model = new RefundGenModel(); 
				BeanUtils.copyProperties ( fileDetailMode, model);
				model.setRefundDate(df3.format(fileDetailMode.getApplyTimes()));
				amount = BnakFileUtil.divideNum(fileDetailMode.getApplyAmount(), new BigDecimal(1000));
				model.setApplyAmount(amount);
				rechargeAmount = BnakFileUtil.divideNum(fileDetailMode.getRechargeAmount(), new BigDecimal(1000));
				model.setRechargeAmount(rechargeAmount);
				model.setApplyRemark("["+fileDetailMode.getDetailKy()+"]"+fileDetailMode.getApplyRemark());
				sumAmount = sumAmount.add(amount);
				model.setSerialNo(String.valueOf(count));
				orderList.add(model);
			}
		ctxMap.put("orderList",orderList);
		String fileName = "cib_"+dateString + "_" + fileGenerateResult.getBatchNum() + "_" + "R1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(TXT_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("cib_gen2r.vm");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("10008001");
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
