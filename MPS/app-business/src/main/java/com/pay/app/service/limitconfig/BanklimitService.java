/**
 *  File: BanklimitService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.limitconfig;

import com.pay.app.dto.BanklimitDTO;
import com.pay.inf.service.BaseService;

/**
 * 
 */
public interface BanklimitService extends BaseService {

	/**
	 * 根据银行Code取限额 配置
	 * 
	 * @param bankCode
	 * @return
	 */
	BanklimitDTO getBanklimitByCode(final String bankCode);
}
