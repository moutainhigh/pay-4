package com.pay.rm.orderthrehold.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.orderfilter.dto.OrderFilterRuleDTO;
import com.pay.rm.orderthrehold.dto.OrderThreholdRuleDTO;


public interface OrderThreholdService {

	boolean updateOrderThreholdRule(OrderThreholdRuleDTO threholdDTO);
	
	OrderThreholdRuleDTO findOrderThreholdRule(String type);
}
