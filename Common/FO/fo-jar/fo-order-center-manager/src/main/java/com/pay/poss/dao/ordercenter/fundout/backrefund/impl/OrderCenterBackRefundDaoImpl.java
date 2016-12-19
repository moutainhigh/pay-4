/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterPay2AccountDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
 */
package com.pay.poss.dao.ordercenter.fundout.backrefund.impl;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.backrefund.OrderCenterBackRefundDao;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-8
 * @see
 */
@SuppressWarnings("unchecked")
public class OrderCenterBackRefundDaoImpl extends BaseDAOImpl implements
		OrderCenterBackRefundDao {
	@Override
	public Page<OrderCenterResultDTO> queryBackRefundResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("backrefund_ordercenter_query", page, dto);
	}

}
