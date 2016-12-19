/**
 *  File: MerchantRequestServiceImpl.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 14, 2011   ch-ma     Create
 *
 */
package com.pay.api.service.impl;

import com.pay.api.model.MerchantRequest;
import com.pay.api.model.MerchantRequestCriteria;
import com.pay.api.service.MerchantRequestService;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 */
public class MerchantRequestServiceImpl implements MerchantRequestService {

	private BaseDAO merchantRequestDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.service.MerchantRequestService#saveMerchantRequestRnTx
	 * (com.pay.fundout.model.MerchantRequest)
	 */
	@Override
	public Long saveMerchantRequestRnTx(MerchantRequest merchantRequest) {

		return (Long) merchantRequestDao.create(merchantRequest);
	}

	public void setMerchantRequestDao(BaseDAO merchantRequestDao) {
		this.merchantRequestDao = merchantRequestDao;
	}

	@Override
	public MerchantRequest findByMerchantCodeAndOrderId(Long merchantCode,
			String orderId) {

		MerchantRequestCriteria criteria = new MerchantRequestCriteria();

		criteria.createCriteria().andMerchantIdEqualTo(
				String.valueOf(merchantCode)).andOrderIdEqualTo(orderId);

		return (MerchantRequest) merchantRequestDao
				.findObjectByCriteria(criteria);
	}

}
