package com.pay.rm.orderfilter.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.orderfilter.dto.OrderFilterRuleDTO;


public interface OrderFilterService {
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateOrderFilterRule(OrderFilterRuleDTO filterDTO);
	
	boolean delete(Long id);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<OrderFilterRuleDTO> findByCriteria(OrderFilterRuleDTO rate,Page page);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<OrderFilterRuleDTO> findByCriteria(OrderFilterRuleDTO rate);
	
	OrderFilterRuleDTO findOrderFilterRule(Map<String,Object> paramMap);
	
	OrderFilterRuleDTO getOrderFilterRule(Map<String,Object> paramMap);
	
	OrderFilterRuleDTO  getOrderFilterRule(OrderFilterRuleDTO markup);
	
	int createOrderFilterRule(OrderFilterRuleDTO ruleDTO);

}
