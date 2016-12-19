package com.pay.gateway.service;

import com.pay.gateway.model.BankDetail;

public interface BankDetailService {

	/**
	 * 根据银行orgcode 查询银行信息
	 * 
	 * @param orgcode
	 * @return
	 */
	public BankDetail queryBankDetail(String orgCode);
}
