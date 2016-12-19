/**
 *  File: ValidateAccountService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount;

import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportFileDTO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * @author darv
 * 
 */
public interface ValidateService {
	/**
	 * 付款方账户状态
	 * @param memberCode
	 * @param accountType
	 * @param applyMoney
	 * @param importFile
	 * @param isValidateRMRule 是否需要验证限额限次
	 * @return
	 */
	public String validatePayerAccount(Long memberCode, Integer accountType, Long applyMoney,
			MasspayImportFileDTO importFile,boolean isValidateRMRule);

	/**
	 * 判断收款方账户状态
	 * 
	 * @param importRecord
	 * @return
	 */
	public String validatePayeeAccount(MasspayImportRecordDTO importRecord,long singleLimit);
	
	/**
	 * 获取指定业务的限额限次规则
	 * @param busiType
	 * @param memberCode
	 * @return
	 */
	public RCLimitResultDTO getRCLimitResult(int busiType,Long memberCode);
}
