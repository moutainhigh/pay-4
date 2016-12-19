 /** @Description 
 * @project 	fo-bankfile
 * @file 		BankFileGenerator.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		Rick_lv			Create 
*/
package com.pay.fundout.bankfile.generator;

import java.util.List;
import java.util.Map;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchFileInfo;

/**
 * <p></p>
 * @author Rick_lv
 * @since 2010-11-9
 * @see 
 */
public interface BankFileGenerator {

	/**
	 * 生成银行明细文件
	 * @param bankDetailInfoList
	 * @param fileInfo
	 * @throws PossException
	 */
	BatchFileInfo generateBankDetailFile(List<FileDetailMode> bankDetailInfoList,Map<String, String> fileInfo)throws PossException;
	
	/**
	 * 支持银行
	 * @param bankCode
	 * @return
	 */
	boolean supportBank(String bankCode);
	
	/**
	 * 支持的业务类型
	 * @return
	 */
	List<String> supportBusiTypeCode();
	
	/**
	 * 支持的交易类型
	 * @return
	 */
	String supportTradeType();
	
	/**
	 * 银行详细文件类型
	 */
	String BATCH_FILE_TYPE_22 = "22";
}
