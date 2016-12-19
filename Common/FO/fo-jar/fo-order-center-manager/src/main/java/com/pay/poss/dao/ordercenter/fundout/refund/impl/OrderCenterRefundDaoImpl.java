/**
 *  File: OrderCenterRefundDaoImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.dao.ordercenter.fundout.refund.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.fundout.refund.OrderCenterRefundDao;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * <p>
 * 查询充退订单结果列表Dao实现
 * </p>
 * 
 * @author Sunsea.Li
 * 
 */
@SuppressWarnings("unchecked")
public class OrderCenterRefundDaoImpl extends BaseDAOImpl implements
		OrderCenterRefundDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.poss.dao.ordercenter.fundout.refund.OrderCenterRefundDao#
	 * queryRefundResultList(com.pay.inf.dao.Page,
	 * com.pay.poss.service.ordercenter.dto.OrderCenterQueryDTO)
	 */
	@Override
	public Page<OrderCenterResultDTO> queryRefundResultList(
			final Page<OrderCenterResultDTO> page, final OrderCenterQueryDTO dto) {
		return super.findByQuery("refund_ordercenter_query",
				page, dto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.poss.dao.ordercenter.fundout.refund.OrderCenterRefundDao#
	 * queryRefundRelationList
	 * (com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO)
	 */
	@Override
	public List<OrderRelationDTO> queryRefundRelationList(
			OrderCenterQueryDTO dto) {
		return super.findByQuery("refund_ordercenter_relation_query", dto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.poss.dao.ordercenter.fundout.refund.OrderCenterRefundDao#
	 * queryRefundOrderDetail(java.lang.Long)
	 */
	@Override
	public OrderDetailDTO queryRefundOrderDetail(Long orderKy) {
		return (OrderDetailDTO) super.findObjectByCriteria("refund_ordercenter_detail_query", orderKy);
	}
}
