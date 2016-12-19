/**
 *  <p>File: PayOnlineDetailServiceFacade.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.service.gateway;

import java.util.List;

import com.pay.poss.dto.fi.PayOnlineDetailDTO;

public interface PayOnlineDetailServiceFacade {

	/**
	 * 
	 * @return
	 */
	public List<PayOnlineDetailDTO> findByTradeOrderInfoId(String fiOrderId);
}
