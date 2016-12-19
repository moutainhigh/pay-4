/**
 *  File: OrderCenterRefundDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.dao.ordercenter.fundout.refund;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * <p>
 * 查询充退订单结果列表Dao接口
 * </p>
 * 
 * @author Sunsea.Li
 * 
 */
public interface OrderCenterRefundDao extends BaseDAO {
	/**
	 * 查询充退交易分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryRefundResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	/**
	 * 订单详情
	 * 
	 * @param id
	 * @return OrderDetailDTO
	 */
	OrderDetailDTO queryRefundOrderDetail(Long orderKy);

	/**
	 * 查询充退关联订单列表
	 * 
	 * @param dto
	 * @return
	 */
	List<OrderRelationDTO> queryRefundRelationList(OrderCenterQueryDTO dto);
}
