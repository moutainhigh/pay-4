/**
 *  File: MerchantResponseServiceImpl.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.pay.api.service.impl;

import com.pay.api.model.MerchantResponse;
import com.pay.api.service.MerchantResponseService;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 */
public class MerchantResponseServiceImpl implements MerchantResponseService {

	private BaseDAO merchantResponseDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.service.MerchantResponseService#saveMerchantResponseRnTx
	 * (com.pay.fundout.model.MerchantResponse)
	 */
	@Override
	public Long saveMerchantResponseRnTx(MerchantResponse merchantResponse) {

		return (Long) merchantResponseDao.create(merchantResponse);
	}

	public void setMerchantResponseDao(BaseDAO merchantResponseDao) {
		this.merchantResponseDao = merchantResponseDao;
	}

}
