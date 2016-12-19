/**
 *  File: OrderCenterRefundServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.common;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.common.OrderCenterTotalDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**<p>查询所有订单结果列表</p>
 * @author Sunsea.Li
 *
 */
public class OrderCenterTotalServiceImpl extends AbstractOrderCenterCommonService{
	private OrderCenterTotalDao orderCenterTotalDao;
	
	public void setOrderCenterTotalDao(OrderCenterTotalDao orderCenterTotalDao) {
		this.orderCenterTotalDao = orderCenterTotalDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterTotalDao.queryTotalResultList(page, dto));
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
