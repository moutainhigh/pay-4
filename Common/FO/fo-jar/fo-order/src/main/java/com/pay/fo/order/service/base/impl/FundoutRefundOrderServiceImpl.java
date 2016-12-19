/**
 * 
 */
package com.pay.fo.order.service.base.impl;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.dao.base.FundoutRefundOrderDAO;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutRefundOrderDTO;
import com.pay.fo.order.model.base.FundoutRefundOrder;
import com.pay.fo.order.model.base.FundoutRefundOrderExample;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class FundoutRefundOrderServiceImpl implements
		com.pay.fo.order.service.base.FundoutRefundOrderService {

	private FundoutRefundOrderDAO fundoutRefundOrderDAO;
	
	@Override
	public Long createOrder(Order order) {
		if(order instanceof FundoutRefundOrderDTO){
			FundoutRefundOrderDTO dto = (FundoutRefundOrderDTO)order;
			dto.setUpdateDate(dto.getCreateDate());
			FundoutRefundOrder model = new FundoutRefundOrder();
			BeanUtils.copyProperties(dto, model);
			return fundoutRefundOrderDAO.insert(model);
		}
		return null;
	}

	@Override
	public boolean updateOrder(Order upOrder) {
		if(upOrder instanceof FundoutRefundOrderDTO){
			FundoutRefundOrderDTO dto = (FundoutRefundOrderDTO)upOrder;
			FundoutRefundOrder model = new FundoutRefundOrder();
			BeanUtils.copyProperties(dto, model);
			
			return fundoutRefundOrderDAO.updateByPrimaryKeySelective(model)==1?true:false;
			
		}
		return false;
	}

	@Override
	public boolean updateOrderStatus(Order upOrder, int oldStatus) {
		if(upOrder instanceof FundoutRefundOrderDTO){
			FundoutRefundOrderDTO dto = (FundoutRefundOrderDTO)upOrder;
			FundoutRefundOrder model = new FundoutRefundOrder();
			model.setOrderStatus(dto.getOrderStatus());
			model.setUpdateDate(dto.getUpdateDate());
			if(!StringUtil.isNull(dto.getUniqueKey())){
				model.setUniqueKey(dto.getUniqueKey());
			}
			FundoutRefundOrderExample example = new FundoutRefundOrderExample();
			  example.createCriteria()
			  .andOrderIdEqualTo(dto.getOrderId())
			  .andOrderStatusEqualTo(oldStatus);

			return fundoutRefundOrderDAO.updateByExampleSelective(model, example)==1;
			
		}
		  
		return false;
	}

	@Override
	public Order getOrder(Long orderId) {
		 FundoutRefundOrder model = fundoutRefundOrderDAO.selectByPrimaryKey(orderId);
		 if(model!=null){
			 FundoutRefundOrderDTO dto = new FundoutRefundOrderDTO();
			 BeanUtils.copyProperties(model, dto);
			 return dto;
		 }
		return null;
	}

	/**
	 * @param fundoutRefundOrderDAO the fundoutRefundOrderDAO to set
	 */
	public void setFundoutRefundOrderDAO(FundoutRefundOrderDAO fundoutRefundOrderDAO) {
		this.fundoutRefundOrderDAO = fundoutRefundOrderDAO;
	}
	
	
	

}
