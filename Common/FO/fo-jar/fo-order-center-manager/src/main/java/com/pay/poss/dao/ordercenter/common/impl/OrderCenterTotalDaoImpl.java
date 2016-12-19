/**
 *  File: OrderCenterRefundDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.dao.ordercenter.common.impl;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.common.OrderCenterTotalDao;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * <p>
 * 查询充退订单结果列表Dao实现
 * </p>
 * 
 * @author Sunsea.Li
 * 
 */
@SuppressWarnings("unchecked")
public class OrderCenterTotalDaoImpl extends BaseDAOImpl<OrderCenterResultDTO>
		implements OrderCenterTotalDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.dao.ordercenter.common.OrderCenterTotalDao#queryTotalResultList
	 * (com.pay.inf.dao.Page,
	 * com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO)
	 */
	@Override
	public Page<OrderCenterResultDTO> queryTotalResultList(
			final Page<OrderCenterResultDTO> page, final OrderCenterQueryDTO dto) {
		return super
				.findByQuery(
						namespace.concat("context_ordercenter_common_query"),
						page, dto);
	}
}
