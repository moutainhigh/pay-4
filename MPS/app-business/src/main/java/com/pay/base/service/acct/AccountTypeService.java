package com.pay.base.service.acct;

import java.util.List;

import com.pay.base.model.AccountType;

/**
 * 
 * 账户类型service
 * 
 * @author jerry_jin
 *
 */
public interface AccountTypeService {

	/**
	 * 根据类型查询账户类型
	 * @param type
	 * @return
	 */
	public List<AccountType> findAccountTypeByType(int type);
	
}
