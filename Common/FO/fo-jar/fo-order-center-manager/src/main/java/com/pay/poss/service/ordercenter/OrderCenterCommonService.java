 /** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterCommonService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-8		Henry.Zeng			Create 
*/
package com.pay.poss.service.ordercenter;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * <p>订单查询commonService</p>
 * @author Henry.Zeng
 * @since 2010-11-8
 * @see 
 */
public interface OrderCenterCommonService {
	/**
	 * 查询订单中心列表
	 * @param page
	 * @param dto   
	 * @return
	 */
	Map<String, Page<OrderCenterResultDTO>> queryResultList(Page<OrderCenterResultDTO> page , OrderCenterQueryDTO dto) ;
	/**
	 * 查询单笔订单明细
	 * @param id
	 * @return
	 */
	OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto);
	
	/**订单历史记录查询
	 * @param queryDto
	 * @return map中会包含一个List<OrderHistoryDTO> historyList
	 */
	Map<String,Object> queryOrderHistory(OrderCenterQueryDTO queryDto);
	
	/**关联订单查询
	 * @param queryDto
	 * @return map中会包含一个List<OrderRelationDTO> relationList
	 */
	Map<String,Object> queryOrderRelation(OrderCenterQueryDTO queryDto);
	
	/**订单分录查询
	 * @param queryDto
	 * @return map中会包含一个List<OrderCenterEntrieDTO> entrieList
	 */
	Map<String,Object> queryOrderEntrie(OrderCenterQueryDTO queryDto);
}
