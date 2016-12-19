/**
 *  File: OrderCenterRefundServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.fundout.impl.pay2account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.pay2account.OrderCenterPay2AccountDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.fundout.impl.withdraw.OrderCenterWithdrawServiceImpl;

/**<p>查询充退订单结果列表</p>
 * @author Sunsea.Li
 *
 */
public class OrderCenterPay2AccountServiceImpl extends AbstractOrderCenterCommonService{
	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private OrderCenterPay2AccountDao orderCenterPay2AccountDao;
	
	public void setOrderCenterPay2AccountDao(OrderCenterPay2AccountDao orderCenterPay2AccountDao) {
		this.orderCenterPay2AccountDao = orderCenterPay2AccountDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterPay2AccountDao.queryPay2AccountResultList(page, dto));
		return map;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {

		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		OrderDetailDTO orderDetailDTO = orderCenterPay2AccountDao
				.queryOrderDetail(orderKy);
		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		// 没有关联订单
		return null;
	}



}
