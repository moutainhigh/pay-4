/**
 *  <p>File: CompositionQueryService.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.service.query;

import java.util.List;
import java.util.Map;

import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.inf.dao.Page;

public interface CommonQueryService {

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public Page<OrderInfo> queryOrdersByCondition(Page<OrderInfo> page,
			final OrderCondition condition);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public List<OrderInfo> queryOrdersByCondition(final OrderCondition condition);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public Map<String, Object> queryOrderReturnMap(Page<OrderInfo> page,
			final OrderCondition condition);

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderInfoDetail queryOrderDetailByOrderId(final Map<String,Object>map);
}
