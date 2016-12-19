package com.pay.acc.service.cidverify;

import com.pay.acc.service.cidverify.dto.IdcBankVerifyDto;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-10-2 下午06:33:37
 */
public interface IdcVerifyBankService {

	/**
	 * 
	 * @param idcBankVerify
	 * @return
	 */
	public IdcBankVerifyDto saveBank(IdcBankVerifyDto idcBankVerify);
}
