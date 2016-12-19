package com.pay.gateway.dao;

import com.pay.gateway.model.BankDetail;

public interface BankDetailDAO {

	/**
	 * 根据银行orgcode 查询银行信息
	 * 
	 * @param orgcode
	 * @return
	 */
	public BankDetail queryBankDetail(String orgCode);

}
