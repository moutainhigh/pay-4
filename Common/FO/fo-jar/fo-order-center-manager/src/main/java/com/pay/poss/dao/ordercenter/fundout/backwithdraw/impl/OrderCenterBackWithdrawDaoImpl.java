/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterPay2AccountDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
 */
package com.pay.poss.dao.ordercenter.fundout.backwithdraw.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.backwithdraw.OrderCenterBackWithdrawDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-8
 * @see
 */
public class OrderCenterBackWithdrawDaoImpl extends BaseDAOImpl implements
		OrderCenterBackWithdrawDao {

	@Override
	public Page<OrderCenterResultDTO> queryBackWithdrawResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_ordercenter_backwithdraw",
				page, dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_backwithdrawdetail",
				orderKy);
	}

	@Override
	public List<OrderRelationDTO> queryRelationList(OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_backwithdraw_relation", dto);
	}

}
