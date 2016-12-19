/** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterPay2AccountDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
 */
package com.pay.poss.dao.ordercenter.fundout.batchpay2account.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.batchpay2account.OrderCenterBatchPay2AccountDao;
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
public class OrderCenterBatchPay2AccountDaoImpl extends BaseDAOImpl implements
		OrderCenterBatchPay2AccountDao {

	@Override
	public Page<OrderCenterResultDTO> queryBatchPay2AccountResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery("batchpay2account_ordercenter_query", page,
				dto);
	}

	@Override
	public List<OrderRelationDTO> queryBatchPay2AccountRelationList(
			OrderCenterQueryDTO dto) {
		return super
				.findByQuery("batchpay2account_ordercenter_relation_query",
						dto);
	}

	@Override
	public OrderDetailDTO queryOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("context_fundout_ordercenter_batchpay2accountdetail",
				orderKy);
	}

}
