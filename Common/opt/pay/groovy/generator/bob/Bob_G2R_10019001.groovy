package com.hnapay.fundout.bankfile.generator.groovy

import org.springframework.beans.BeanUtils

import com.hnapay.fundout.bankfile.common.util.BnakFileUtil
import com.hnapay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.hnapay.fundout.bankfile.generator.helper.FileGenerateResult
import com.hnapay.fundout.bankfile.generator.model.FileDetailMode
import com.hnapay.fundout.bankfile.generator.model.FileSummaryModel
import com.hnapay.fundout.bankfile.generator.model.refund.RefundGenModel

/**		
 *  @author wucan
 *  @Date 2013-05-22
 *  @Description 北京银行充退文件B2C
 *  @Copyright Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 *  订单日期（YYYYMMDD）	订单号	退款金额（####.##）
 */
class Bob_G2R_10019002 extends AbstractBankFileGenerator {

	@Override
	public boolean supportBank(String bankCode) {
		return "10019001".equals(bankCode);
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
				model.setRechargeBankOrder(fileDetailMode.getRechargeBankOrder()+"\t")
				model.setRefundDate(df3.format(fileDetailMode.getApplyTimes()));
				amount = BnakFileUtil.divideNum(fileDetailMode.getApplyAmount(), new BigDecimal(1000));
				model.setApplyAmount(amount);
				model.setApplyRemark("["+fileDetailMode.getDetailKy()+"]"+fileDetailMode.getApplyRemark());
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		ctxMap.put("orderList",orderList);
		String fileName = "bob_"+dateString + "_" + fileGenerateResult.getBatchNum() + "_" + "R1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(".xls");
		// 获取文件全路径并生成相应的路径
		genRfFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("bob_gen2r.xls");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("10019001");
		ctxMap.put("summer",summaryModel);
		
		fileGenerateResult.setCtxMap(ctxMap);
		//生成模板文件
		writeExcelFile(fileGenerateResult);
		//构建批次信息
		sumAmount = sumAmount.multiply(new BigDecimal(10))
		summaryModel.setTotalAmount(sumAmount);
		buildBatchFileInfo(fileGenerateResult, summaryModel);
	}

}
