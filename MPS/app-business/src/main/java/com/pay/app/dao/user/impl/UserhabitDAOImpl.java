/**
 *  File: UserhabitDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.user.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.app.dao.user.UserhabitDAO;
import com.pay.app.model.Userhabit;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class UserhabitDAOImpl extends BaseDAOImpl implements UserhabitDAO {

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	public Userhabit findLastLoginByMemberCode(final String memberCode) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		return (Userhabit) super.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("findByMemberCode"), paraMap);
	}
}
