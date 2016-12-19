package com.pay.fundout.bankfile.generator.groovy

import java.text.DecimalFormat

import org.springframework.beans.BeanUtils

import com.pay.fundout.bankfile.common.util.BankFileUtil
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult
import com.pay.fundout.bankfile.generator.model.FileSummaryModel
import com.pay.fundout.bankfile.generator.model.FileDetailMode
import com.pay.fundout.bankfile.generator.model.cmbc.CmbcGeneratorModel

/**		
 *  @author mmzhang
 *  @Date 2011-7-29
 *  @Description 民生银行充退解析器 txt
 *  @Copyright Copyright © 2015-2020 ipaylinks.com . All rights reserved.
 */
class Cmbc_G2R extends AbstractBankFileGenerator{
	
/*
上传文件模版说明：客户在填写模板文件时，按如下方式填写
流水号,订单号,退单金额,积分,

示例：
001,01001080721164141,20.00,0,
002,01001080721171218,10.00,0,
003,01001080721174657,10.00,0,
004,01001080721174815,10.00,0,

****************************************
注意事项:
1.批次号不得超过11位,且不能重复;
2.文件名长度不得超过30个字符(中文名是15个);
3.摘要长度不得超过80个字符(中文是40个);
4.数据域依次是流水号、订单号、退单金额、退单积分，行尾要有","逗号（格式要用英文）；
5.退单文件整体格式要求：每一行为一条退单流水，文件开始和中间不可有空行;
6.流水号最大3位长，单个退单批次中不可重复;
7.退单金额精确到2位小数，不能带有正/负(+/-)符号，不可为空，可以填0.00;
8.退单积分为整数，不能带有正/负(+/-)符号，不可为空，如无退单积分则该项填0;
*/
	
	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	*/
   @Override
   public boolean supportBank(String bankCode) {
	   return "100070011".equals(bankCode);
   }

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		return "1";
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.pay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		def size = fileGenerateResult.getDetailList().size();
		BigDecimal sumAmount =  0;
		BigDecimal amount = 0
		CmbcGeneratorModel model = null; 
		Map<String,Object> ctxMap = new HashMap<String, Object>();
		String dateString = df3.format(new Date());
		
		List<CmbcGeneratorModel> orderList = new ArrayList<CmbcGeneratorModel>(size);
		def count = 0;
		DecimalFormat df = new DecimalFormat("000");
		for(FileDetailMode fileDetailMode in fileGenerateResult.getDetailList()){
				++count;
				model = new CmbcGeneratorModel(); 
				BeanUtils.copyProperties ( fileDetailMode, model);
				//金额
				amount = BankFileUtil.divideNum(model.getAmount(),new BigDecimal(1000));
				model.setAmount(amount);
				model.setSerialNo(df.format(count));
				sumAmount = sumAmount.add(amount);
				orderList.add(model);
			}
		ctxMap.put("list",orderList);
		String fileName = "cmbc_${dateString}_${fileGenerateResult.getBatchNum()}_1";
		fileGenerateResult.setFileName(fileName);
		fileGenerateResult.setFileSubfix(XLS_FILE_SUBFIX);
		fileGenerateResult.setGenFileEncoding(ENCODING_ANSI);
		// 获取文件全路径并生成相应的路径
		genFoFullPath(fileGenerateResult);
		fileGenerateResult.setTemplateName("cmbc_gen.xls");
		
		FileSummaryModel summaryModel = new FileSummaryModel();
		summaryModel.setTotalCount(size);
		summaryModel.setTotalAmount(sumAmount);
		summaryModel.setBankCode("10007001");
		if(size>1)
		{
		summaryModel.setAuditType("1");
		}else{
		summaryModel.setAuditType("0");
		}
		ctxMap.put("summer",summaryModel);
		
		fileGenerateResult.setCtxMap(ctxMap)
		//生成模板文件
		writeExcelFile(fileGenerateResult);
		//构建批次信息
		sumAmount = sumAmount.multiply(new BigDecimal(1000))
		summaryModel.setTotalAmount(sumAmount);
		buildBatchFileInfo(fileGenerateResult, summaryModel);

	}

}
