package com.pay.poss.dao.ordercenter.ma.samismile.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.dao.ordercenter.ma.samismile.OrderCenterSamismileDao;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 
 * @Description
 * @project fo-order-center-manager
 * @file OrderCenterSamismileDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-12-09 gungun_zhang Create
 */

public class OrderCenterSamismileDaoImpl extends BaseDAOImpl implements
		OrderCenterSamismileDao {

	@Override
	public Page<OrderCenterResultDTO> querySamismileList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		return super.findByQuery(namespace.concat("ma_queryOrder"), page, dto);
	}

	@Override
	public List<OrderRelationDTO> querySamismileRelationList(String orderId,
			String orderType) {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("orderId", orderId);
		dataMap.put("orderType", orderType);
		return super.findByQuery(namespace.concat("ma_queryOrderRelation"),
				dataMap);

	}

}
