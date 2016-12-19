package com.pay.gateway.dao.impl;

import com.pay.gateway.dao.BankDetailDAO;
import com.pay.gateway.model.BankDetail;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class BankDetailDAOImpl extends BaseDAOImpl<BankDetail> implements
		BankDetailDAO {

	/**
	 * 根据银行orgcode 查询银行信息
	 * 
	 * @param orgcode
	 * @return
	 */
	public BankDetail queryBankDetail(String orgCode) {
		BankDetail bankDetail = (BankDetail) getSqlMapClientTemplate()
				.queryForObject("bankDetail.selectbankDetailResultWithOrgCode",
						orgCode);
		return bankDetail;
	}

}
