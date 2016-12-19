package com.pay.fundout.dao.query;

import java.util.List;
import java.util.Map;

import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface CommonQueryDao extends BaseDAO {

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public Page<OrderInfo> queryOrdersByCondition(final String sqlId,
			Page<OrderInfo> page, final OrderCondition condition);

	/**
	 * 
	 * @param sqlId
	 * @param condition
	 * @return
	 */
	public List<OrderInfo> queryOrdersByCondition(final String sqlId,
			final OrderCondition condition);

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderInfoDetail queryOrderDetailByOrderId(final String sqlId,
			final Map<String, Object> map);
}
