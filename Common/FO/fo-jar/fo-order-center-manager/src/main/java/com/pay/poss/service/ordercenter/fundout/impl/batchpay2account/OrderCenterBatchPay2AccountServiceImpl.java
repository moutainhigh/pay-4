/**
 *  File: OrderCenterRefundServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.fundout.impl.batchpay2account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.batchpay2account.OrderCenterBatchPay2AccountDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.fundout.impl.withdraw.OrderCenterWithdrawServiceImpl;

/**<p>批量付款到账户</p>
 * @author Sunsea.Li
 *
 */
public class OrderCenterBatchPay2AccountServiceImpl extends AbstractOrderCenterCommonService{
	
	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private OrderCenterBatchPay2AccountDao orderCenterBatchPay2AccountDao;
	
	public void setOrderCenterBatchPay2AccountDao(OrderCenterBatchPay2AccountDao orderCenterBatchPay2AccountDao) {
		this.orderCenterBatchPay2AccountDao = orderCenterBatchPay2AccountDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterBatchPay2AccountDao.queryBatchPay2AccountResultList(page, dto));
		return map;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {

		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		OrderDetailDTO orderDetailDTO = orderCenterBatchPay2AccountDao
				.queryOrderDetail(orderKy);
		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterBatchPay2AccountDao.queryBatchPay2AccountRelationList(queryDto));
		return map;
	}



}
