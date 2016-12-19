/**
 *  File: UserhabitService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.user;

import com.pay.app.dto.UserhabitDTO;

/**
 * 
 */
public interface UserhabitService {

	/**
	 * 获取用户最后一次的行为习惯
	 * 
	 * @param memberCode
	 * @return
	 */
	UserhabitDTO findLastLoginByMemberCode(final String memberCode);

	/**
	 * 
	 * @return
	 */
	UserhabitDTO saveUserhabitRnTx(UserhabitDTO userhabit);

	/**
	 * 获取用户上一次支付银行.
	 * 
	 * @param memberCode
	 * @param bankList
	 * @return
	 */
	// EBankInfoDto getLastPayBank(final String memberCode);

	/**
	 * 获取用户上一次支付银行.
	 * 
	 * @param memberCode
	 * @param bankList
	 * @return
	 */
	// EBankInfoDto getBankDtoBybankCode(final String bankCode);
}
