/**
 * 
 */
package com.pay.poss.accounting.dao;

import com.pay.inf.dao.Page;
import com.pay.poss.accounting.dto.AccountingFeeDto;
import com.pay.poss.accounting.dto.AccountingFeeParamDto;

/**
 * @Description
 * @project ma-manager
 * @file AccoutingFeeDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Date Author Changes 2012-3-2 DDR Create
 */
public interface AccountingFeeDao {

	/**
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<AccountingFeeDto> findPage(Page<AccountingFeeDto> page,
			AccountingFeeParamDto dto);

	/**
	 * 统计查询的结果有几例
	 * 
	 * @param dto
	 * @return
	 */
	int countSearch(AccountingFeeParamDto dto);

}
