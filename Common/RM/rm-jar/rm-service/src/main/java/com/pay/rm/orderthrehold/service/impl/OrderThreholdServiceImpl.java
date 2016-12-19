package com.pay.rm.orderthrehold.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.orderfilter.dto.OrderFilterRuleDTO;
import com.pay.rm.orderthrehold.dao.OrderThreholdDAO;
import com.pay.rm.orderthrehold.dto.OrderThreholdRuleDTO;
import com.pay.rm.orderthrehold.service.OrderThreholdService;

public class OrderThreholdServiceImpl implements OrderThreholdService{
	private OrderThreholdDAO orderThreholdDAO;

	public void setOrderThreholdDAO(OrderThreholdDAO orderThreholdDAO) {
		this.orderThreholdDAO = orderThreholdDAO;
	}

	@Override
	public boolean updateOrderThreholdRule(OrderThreholdRuleDTO threholdDTO) {
		return orderThreholdDAO.update(threholdDTO);
	}
	
	@Override
	public OrderThreholdRuleDTO findOrderThreholdRule(String type) {
		return orderThreholdDAO.findById(type);
	}
}
