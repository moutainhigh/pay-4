/**
 *  <p>File: RefundOnlineServiceFacadeImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.service.gateway.impl;

import java.util.List;

import com.pay.poss.dao.ExternalFacadeDao;
import com.pay.poss.dto.fi.RefundOnlineDTO;
import com.pay.poss.service.gateway.RefundOnlineServiceFacade;

public class RefundOnlineServiceFacadeImpl implements RefundOnlineServiceFacade {

	private ExternalFacadeDao externalFacadeDao;

	@Override
	public List<RefundOnlineDTO> findByRefundOrderInfoId(
			final String refundOrderInfoId) {
	
		return externalFacadeDao.findByRefundOrderInfoId(refundOrderInfoId);
	}

	public void setExternalFacadeDao(ExternalFacadeDao externalFacadeDao) {
		this.externalFacadeDao = externalFacadeDao;
	}

}
