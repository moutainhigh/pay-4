package com.pay.poss.dao.ordercenter.ma.samismile;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 
 * @Description
 * @project fo-order-center-manager
 * @file OrderCenterSamismileDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-12-09 gungun_zhang Create
 */
public interface OrderCenterSamismileDao extends BaseDAO {
	public Page<OrderCenterResultDTO> querySamismileList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto);

	public List<OrderRelationDTO> querySamismileRelationList(String orderId,
			String orderType);

}
