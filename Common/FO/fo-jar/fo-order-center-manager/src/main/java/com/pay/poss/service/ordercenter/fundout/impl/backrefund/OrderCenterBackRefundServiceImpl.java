/**
 *  File: OrderCenterRefundServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.fundout.impl.backrefund;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.backrefund.OrderCenterBackRefundDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**<p>查询充退订单结果列表</p>
 * @author Sunsea.Li
 *
 */
public class OrderCenterBackRefundServiceImpl extends AbstractOrderCenterCommonService{
	private OrderCenterBackRefundDao orderCenterBackRefundDao;
	
	public void setOrderCenterBackRefundDao(OrderCenterBackRefundDao orderCenterBackRefundDao) {
		this.orderCenterBackRefundDao = orderCenterBackRefundDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterBackRefundDao.queryBackRefundResultList(page, dto));
		return map;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		return null;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		// TODO Auto-generated method stub
		return null;
	}



}
