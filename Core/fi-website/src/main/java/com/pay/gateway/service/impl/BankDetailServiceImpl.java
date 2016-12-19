package com.pay.gateway.service.impl;

import com.pay.gateway.dao.BankDetailDAO;
import com.pay.gateway.model.BankDetail;
import com.pay.gateway.service.BankDetailService;

public class BankDetailServiceImpl implements BankDetailService {

	private BankDetailDAO bankDetailDAO;

	@Override
	public BankDetail queryBankDetail(String orgCode) {
		return this.bankDetailDAO.queryBankDetail(orgCode);
	}

	public void setBankDetailDAO(BankDetailDAO bankDetailDAO) {
		this.bankDetailDAO = bankDetailDAO;
	}

}
