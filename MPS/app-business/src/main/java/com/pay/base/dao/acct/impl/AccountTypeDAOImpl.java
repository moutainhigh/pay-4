package com.pay.base.dao.acct.impl;

import java.util.List;

import com.pay.base.dao.acct.AccountTypeDAO;
import com.pay.base.model.AccountType;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class AccountTypeDAOImpl extends BaseDAOImpl implements AccountTypeDAO {

	@Override
	public List<AccountType> findAccountTypeByType(int type) {
		return this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("findAccountTypeByType"), type);
	}

	public Class<?> getModelClass() {
		return AccountType.class;
	}

}
