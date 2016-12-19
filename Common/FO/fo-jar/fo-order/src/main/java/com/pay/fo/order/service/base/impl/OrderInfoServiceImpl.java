/**
 * 
 */
package com.pay.fo.order.service.base.impl;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.dao.base.OrderInfoDAO;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.model.base.OrderInfo;
import com.pay.fo.order.model.base.OrderInfoExample;
import com.pay.fo.order.service.base.OrderInfoService;

/**
 * @author NEW
 *
 */
public class OrderInfoServiceImpl implements OrderInfoService {
	
	private OrderInfoDAO orderInfoDAO;
	

	/**
	 * @param orderInfoDAO the orderInfoDAO to set
	 */
	public void setOrderInfoDAO(OrderInfoDAO orderInfoDAO) {
		this.orderInfoDAO = orderInfoDAO;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.OrderService#createOrder(com.pay.fo.order.dto.Order)
	 */
	@Override
	public Long createOrder(Order order) {
		
		if(order instanceof OrderInfoDTO){
			OrderInfoDTO dto = (OrderInfoDTO)order;
			OrderInfo model = new OrderInfo();
			BeanUtils.copyProperties(dto, model);
			orderInfoDAO.insert(model);
			return model.getOrderId();
		}
		return null;
	}

	@Override
	public boolean updateOrder(Order upOrder) {
		if(upOrder instanceof OrderInfoDTO){
			OrderInfoDTO dto = (OrderInfoDTO)upOrder;
			OrderInfo model = new OrderInfo();
			BeanUtils.copyProperties(dto, model);
			
			return orderInfoDAO.updateByPrimaryKeySelective(model)==1?true:false;
			
		}
		return false;
	}
	
	@Override
	public boolean updateOrderStatus(Order upOrder, int oldStatus) {
		
		if(upOrder instanceof OrderInfoDTO){
			OrderInfoDTO dto = (OrderInfoDTO)upOrder;
			OrderInfo model = new OrderInfo();
			model.setOrderStatus(dto.getOrderStatus());
			model.setUpdateDate(dto.getUpdateDate());
			model.setFailedReason(dto.getFailedReason());
			
			OrderInfoExample example = new OrderInfoExample();
			  example.createCriteria()
			  .andOrderIdEqualTo(dto.getOrderId())
			  .andOrderStatusEqualTo(oldStatus);

			return orderInfoDAO.updateByExampleSelective(model, example)==1?true:false;
			
		}
		  
		return false;
	}


	@Override
	public Order getOrder(Long orderId) {
		 OrderInfo model = orderInfoDAO.selectByPrimaryKey(orderId);
		 if(model!=null){
			 OrderInfoDTO dto = new OrderInfoDTO();
			 BeanUtils.copyProperties(model, dto);
			 return dto;
		 }
		return null;
	}

	

	

}
