/**
 *  File: BanklimitDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.limitconfig.impl;

import com.pay.app.dao.limitconfig.BanklimitDAO;
import com.pay.app.model.Banklimit;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class BanklimitDAOImpl extends BaseDAOImpl implements BanklimitDAO {

	/**
	 * 根据银行代码获取限额
	 * 
	 * @param bankCode
	 * @return
	 */
	public Banklimit getBanklimitByCode(final String bankCode) {

		return (Banklimit) super.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryByBankCode"), bankCode);
	}
}
