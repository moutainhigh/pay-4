/**
 *  File: UserhabitDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.user;

import com.pay.app.model.Userhabit;

/**
 * 
 */
public interface UserhabitDAO {

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	Userhabit findLastLoginByMemberCode(final String memberCode);
}