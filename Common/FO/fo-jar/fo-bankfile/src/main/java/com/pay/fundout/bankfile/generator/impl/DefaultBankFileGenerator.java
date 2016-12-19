package com.pay.fundout.bankfile.generator.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator;
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult;
import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchFileInfo;
/**
 * 银联文件生成器
 * @author zengli
 * @since 2011-4-30
 * @see
 */
public class DefaultBankFileGenerator extends AbstractBankFileGenerator {
	@Override
	public BatchFileInfo generateBankDetailFile(List<FileDetailMode> bankDetailInfoList,
			Map<String, String> fileInfo) throws PossException {
		return null;
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
