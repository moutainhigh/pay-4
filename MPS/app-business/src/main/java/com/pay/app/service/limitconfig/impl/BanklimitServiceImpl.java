/**
 *  File: BanklimitServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.limitconfig.impl;

import com.pay.app.dao.limitconfig.BanklimitDAO;
import com.pay.app.dto.BanklimitDTO;
import com.pay.app.model.Banklimit;
import com.pay.app.service.limitconfig.BanklimitService;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class BanklimitServiceImpl extends BaseServiceImpl implements
		BanklimitService {

	public Class getDtoClass() {

		return BanklimitDTO.class;
	}

	/**
	 * 根据银行Code取限额 配置
	 * 
	 * @param bankCode
	 * @return
	 */
	public BanklimitDTO getBanklimitByCode(final String bankCode) {

		BanklimitDAO banklimitDAO = (BanklimitDAO) mainDao;
		return BeanConvertUtil.convert(BanklimitDTO.class,
				banklimitDAO.getBanklimitByCode(bankCode));
	}

	@Override
	protected Class getModelClass() {
		return Banklimit.class;
	}
}
