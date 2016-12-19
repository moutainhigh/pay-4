/**
 *  <p>File: PayOnlineDetailServiceFacadeImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.service.gateway.impl;

import java.util.List;

import com.pay.poss.dao.ExternalFacadeDao;
import com.pay.poss.dto.fi.PayOnlineDetailDTO;
import com.pay.poss.service.gateway.PayOnlineDetailServiceFacade;

public class PayOnlineDetailServiceFacadeImpl implements
		PayOnlineDetailServiceFacade {

	private ExternalFacadeDao externalFacadeDao;

	public void setExternalFacadeDao(ExternalFacadeDao externalFacadeDao) {
		this.externalFacadeDao = externalFacadeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.poss.service.gateway.PayOnlineDetailServiceFacade#
	 * findByTradeOrderInfoId()
	 */
	@Override
	public List<PayOnlineDetailDTO> findByTradeOrderInfoId(String fiOrderId) {

		return externalFacadeDao.findByTradeOrderInfoId(fiOrderId);
	}

}
