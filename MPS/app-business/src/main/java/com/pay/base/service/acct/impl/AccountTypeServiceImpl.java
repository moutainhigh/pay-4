package com.pay.base.service.acct.impl;

import java.util.List;

import com.pay.base.dao.acct.AccountTypeDAO;
import com.pay.base.model.AccountType;
import com.pay.base.service.acct.AccountTypeService;

public class AccountTypeServiceImpl implements AccountTypeService {
	
	private AccountTypeDAO accountTypeDAO;

	@Override
	public List<AccountType> findAccountTypeByType(int type) {
		return accountTypeDAO.findAccountTypeByType(type);
	}

	public void setAccountTypeDAO(AccountTypeDAO accountTypeDAO) {
		this.accountTypeDAO = accountTypeDAO;
	}

}
