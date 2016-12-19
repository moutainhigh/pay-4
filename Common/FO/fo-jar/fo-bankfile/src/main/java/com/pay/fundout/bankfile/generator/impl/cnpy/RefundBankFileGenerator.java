/**
 *  File: RefundBankFileGenerator4CP.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.bankfile.generator.impl.cnpy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.fundout.bankfile.common.util.BankFileConstants;
import com.pay.fundout.bankfile.common.util.CreatorFileDirUtil;
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator;
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult;
import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.fundout.bankfile.generator.model.FileSummaryModel;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.util.DateUtil;

public class RefundBankFileGenerator extends AbstractBankFileGenerator {

	private static final String SEQARATOR_CH = "|";
	private static final String UNDERLINE_CH = "_";
	private static final String BANK_CODE = "800";
	private static final String BANK_CODE_T = "11001001";
	private static final String ACCT_TYPE_ALL = "ALL";
	private static final String FILE_BUSI_TYPE_ALL = "2";
	private List<String> supportBusiCode = new ArrayList<String>();
	private static String PAY_ACCT_CNPY;
	
	public RefundBankFileGenerator(){
		supportBusiCode.add("10");
		PAY_ACCT_CNPY = CommonConfiguration.getStrProperties(BankFileConstants.PAYMENT_ACCT_CNPY);
	}

	@Override
	public BatchFileInfo generateBankDetailFile(List<FileDetailMode> bankDetailInfoList, Map<String, String> fileInfo) throws PossException {
		if (null == bankDetailInfoList || bankDetailInfoList.isEmpty() || null == fileInfo) {
			return null;
		}
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".txt");
		fileInfo.put("filetype", BATCH_FILE_TYPE_22);// 外部详细文件
		int size = bankDetailInfoList.size();
		StringBuffer fileBody = new StringBuffer();
		long sumAmount = 0;
		String batchNum = "";
		String bankCode = "";
		FileDetailMode tempInfo = null;
		// 组装银联文件内容
		for (int i = 0; i < size; i++) {
			tempInfo = (FileDetailMode) bankDetailInfoList.get(i);
			sumAmount += tempInfo.getApplyAmount().longValue();
			if (i == 0) {
				batchNum = tempInfo.getBatchNum();
				bankCode = tempInfo.getRechargeBank();
			}

			fileBody.append(DateUtil.formatDateTime("yyyyMMdd", tempInfo.getApplyTimes()) + SEQARATOR_CH);
			fileBody.append(tempInfo.getRechargeBankOrder() + SEQARATOR_CH);
			fileBody.append(tempInfo.getApplyAmount().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN) + SEQARATOR_CH);

			if (i < (size - 1)) {
				fileBody.append("\r\n");
			}
		}

		String fileContent = size + SEQARATOR_CH + new BigDecimal(sumAmount).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN) + "\r\n" + fileBody.toString();
		String fileName = PAY_ACCT_CNPY + UNDERLINE_CH + DateUtil.formatDateTime("yyyyMMdd", new Date()) + UNDERLINE_CH + batchNum;
		fileInfo.put("FILE_NAME", fileName);
		// 写入文件
		String filePath = CreatorFileDirUtil.writeFileForRefund(fileContent.toString(), fileInfo);

		FileSummaryModel refundBatchInfoDTO = new FileSummaryModel();

		refundBatchInfoDTO.setBankCode(bankCode);
		refundBatchInfoDTO.setTotalCount(size);
		refundBatchInfoDTO.setTotalAmount(new BigDecimal(sumAmount));
		refundBatchInfoDTO.setBatchNum(batchNum);
		
		BatchFileInfo batchInfo = CreatorFileDirUtil.bulidBatchFileInfo(refundBatchInfoDTO, filePath, fileInfo);
		batchInfo.setFileBusiType(FILE_BUSI_TYPE_ALL);
		return batchInfo;
	}


	@Override
	public List<String> supportBusiTypeCode() {
		return supportBusiCode;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportBank(java.lang.String)
	 */
	@Override
	public boolean supportBank(String bankCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.BankFileGenerator#supportTradeType()
	 */
	@Override
	public String supportTradeType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.generator.AbstractBankFileGenerator#buildBankDetailList(com.pay.fundout.bankfile.generator.helper.FileGenerateResult)
	 */
	@Override
	protected void buildBankDetailList(FileGenerateResult fileGenerateResult) {
		// TODO Auto-generated method stub
		
	}


}
