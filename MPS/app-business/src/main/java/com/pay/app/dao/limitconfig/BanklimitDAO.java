/**
 *  File: BanklimitDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.dao.limitconfig;

import com.pay.app.model.Banklimit;

/**
 * 
 */
public interface BanklimitDAO {

	/**
	 * 根据银行代码获取限额
	 * 
	 * @param bankCode
	 * @return
	 */
	Banklimit getBanklimitByCode(final String bankCode);
}
