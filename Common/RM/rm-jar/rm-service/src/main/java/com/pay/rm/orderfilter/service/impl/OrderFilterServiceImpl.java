package com.pay.rm.orderfilter.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.rm.orderfilter.dao.OrderFilterDAO;
import com.pay.rm.orderfilter.dto.OrderFilterRuleDTO;
import com.pay.rm.orderfilter.service.OrderFilterService;

public class OrderFilterServiceImpl implements OrderFilterService{
	
	private static final Log logger = LogFactory.getLog(OrderFilterServiceImpl.class) ;
	
	private OrderFilterDAO orderFilterDAO;

	public void setOrderFilterDAO(OrderFilterDAO orderFilterDAO) {
		this.orderFilterDAO = orderFilterDAO;
	}

	@Override
	public boolean updateOrderFilterRule(OrderFilterRuleDTO filterDTO) {
		return orderFilterDAO.update(filterDTO);
	}

	@Override
	public List<OrderFilterRuleDTO> findByCriteria(OrderFilterRuleDTO rule,
			Page page) {
		return orderFilterDAO.findByCriteria("findByCriteria",rule,page);
	}

	@Override
	public List<OrderFilterRuleDTO> findByCriteria(OrderFilterRuleDTO rule) {
		return orderFilterDAO.findByCriteria("findByCriteria",rule);
	}

	@Override
	public OrderFilterRuleDTO findOrderFilterRule(Map<String, Object> paramMap) {
		List<OrderFilterRuleDTO> list = orderFilterDAO.findByCriteria("findByMap",paramMap);
		
		if(list!=null&&list.size()>0){
			logger.info("订单过滤命中规则共【" + list.size() + "条】");
			logger.info("规则详细：" + list.toString());
			return list.get(0);
		}
			
		return null;
	}

	@Override
	public OrderFilterRuleDTO getOrderFilterRule(OrderFilterRuleDTO markup) {
		List<OrderFilterRuleDTO> list = orderFilterDAO.findByCriteria("findByCriteria",markup);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public OrderFilterRuleDTO getOrderFilterRule(Map<String, Object> paramMap) {
		OrderFilterRuleDTO filterRuleDTO = this.findOrderFilterRule(paramMap);
		if(filterRuleDTO==null){
			paramMap.put("memberCode","0");
			filterRuleDTO = this.findOrderFilterRule(paramMap);
		}
		return filterRuleDTO;
	}

	@Override
	public int createOrderFilterRule(OrderFilterRuleDTO ruleDTO) {
		orderFilterDAO.create(ruleDTO);
		return 1;
	}

	@Override
	public boolean delete(Long id) {
		return orderFilterDAO.delete("deleteById",id);
	}
	

}
