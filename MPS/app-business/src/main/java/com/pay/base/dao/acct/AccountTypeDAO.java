package com.pay.base.dao.acct;

import java.util.List;

import com.pay.base.model.AccountType;

/**
 * 
 * @author jerry_jin
 *
 */
public interface AccountTypeDAO {

	/**
	 * 根据类型查询账户类型
	 * @param type
	 * @return
	 */
	public List<AccountType> findAccountTypeByType(int type);
	
}
