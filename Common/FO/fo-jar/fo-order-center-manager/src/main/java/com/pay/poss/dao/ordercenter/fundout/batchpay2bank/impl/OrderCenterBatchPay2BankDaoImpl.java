/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterPay2AccountDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
 */
package com.pay.poss.dao.ordercenter.fundout.batchpay2bank.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.batchpay2bank.OrderCenterBatchPay2BankDao;
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
@SuppressWarnings("unchecked")
public class OrderCenterBatchPay2BankDaoImpl extends BaseDAOImpl implements
		OrderCenterBatchPay2BankDao {

	@Override
	public Page<OrderCenterResultDTO> queryBatchPay2BankResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("context_fundout_ordercenter_pay2banks",
				page, dto);
	}

	@Override
	public List<OrderRelationDTO> queryBatchPay2BankRelationList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery("withdraw_ordercenter_relation_query", dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_orderdetail",
				orderKy);
	}

}
