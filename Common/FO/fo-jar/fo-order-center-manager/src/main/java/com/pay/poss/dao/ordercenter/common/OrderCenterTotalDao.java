/**
 *  File: OrderCenterRefundDao.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.dao.ordercenter.common;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * <p>
 * 查询充退订单结果列表Dao接口
 * </p>
 * 
 * @author Sunsea.Li
 * 
 */
public interface OrderCenterTotalDao extends BaseDAO<OrderCenterResultDTO> {
	/**
	 * 查询充退交易分页列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	Page<OrderCenterResultDTO> queryTotalResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);
}
