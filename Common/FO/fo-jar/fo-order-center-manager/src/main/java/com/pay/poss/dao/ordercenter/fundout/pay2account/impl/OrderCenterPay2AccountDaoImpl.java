/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterPay2AccountDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
 */
package com.pay.poss.dao.ordercenter.fundout.pay2account.impl;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.pay2account.OrderCenterPay2AccountDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
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
public class OrderCenterPay2AccountDaoImpl extends BaseDAOImpl implements
		OrderCenterPay2AccountDao {

	@Override
	public Page<OrderCenterResultDTO> queryPay2AccountResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("pay2account_ordercenter_query", page, dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_pay2accountdetail",
				orderKy);
	}

}
