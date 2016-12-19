/**
 * 
 */
package com.pay.fo.order.service.base.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.dao.base.PayToAcctOrderDAO;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.model.base.PayToAcctOrder;
import com.pay.fo.order.model.base.PayToAcctOrderExample;
import com.pay.fo.order.service.base.PayToAcctOrderService;

/**
 * @author NEW
 *
 */
public class PayToAcctOrderServiceImpl implements PayToAcctOrderService {

	
	private PayToAcctOrderDAO payToAcctOrderDAO; 
	
	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.OrderService#createOrder(com.pay.fo.order.dto.Order)
	 */
	@Override
	public Long createOrder(Order order) {
		if(order instanceof PayToAcctOrderDTO){
			PayToAcctOrderDTO dto = (PayToAcctOrderDTO)order;
			PayToAcctOrder model = new PayToAcctOrder();
			BeanUtils.copyProperties(dto, model);
			return payToAcctOrderDAO.insert(model);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.OrderService#updateOrder(com.pay.fo.order.dto.Order)
	 */
	@Override
	public boolean updateOrder(Order upOrder) {
		if(upOrder instanceof PayToAcctOrderDTO){
			PayToAcctOrderDTO dto = (PayToAcctOrderDTO)upOrder;
			PayToAcctOrder model = new PayToAcctOrder();
			BeanUtils.copyProperties(dto, model);
			
			return payToAcctOrderDAO.updateByPrimaryKeySelective(model)==1?true:false;
			
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.OrderService#updateOrderStatus(com.pay.fo.order.dto.Order, int)
	 */
	@Override
	public boolean updateOrderStatus(Order upOrder, int oldStatus) {
		if(upOrder instanceof PayToAcctOrderDTO){
			PayToAcctOrderDTO dto = (PayToAcctOrderDTO)upOrder;
			PayToAcctOrder model = new PayToAcctOrder();
			model.setOrderStatus(dto.getOrderStatus());
			model.setUpdateDate(dto.getUpdateDate());
			model.setFailedReason(dto.getFailedReason());
			
			PayToAcctOrderExample example = new PayToAcctOrderExample();
			  example.createCriteria()
			  .andOrderIdEqualTo(dto.getOrderId())
			  .andOrderStatusEqualTo(oldStatus);

			return payToAcctOrderDAO.updateByExampleSelective(model, example)==1?true:false;
			
		}
		  
		return false;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.OrderService#getOrder(java.lang.Long)
	 */
	@Override
	public Order getOrder(Long orderId) {
		 PayToAcctOrder model = payToAcctOrderDAO.selectByPrimaryKey(orderId);
		 if(model!=null){
			 PayToAcctOrderDTO dto = new PayToAcctOrderDTO();
			 BeanUtils.copyProperties(model, dto);
			 return dto;
		 }
		return null;
	}

	/**
	 * @param payToAcctOrderDAO the payToAcctOrderDAO to set
	 */
	public void setPayToAcctOrderDAO(PayToAcctOrderDAO payToAcctOrderDAO) {
		this.payToAcctOrderDAO = payToAcctOrderDAO;
	}

	@Override
	public Long sumCurrentDayPaymentAmount(Integer orderType,
			Long payerMemberCode) {
		PayToAcctOrder record = new PayToAcctOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		
		return payToAcctOrderDAO.sumCurrentDayPaymentAmount(record);
	}

	@Override
	public Long sumCurrentMonthPaymentAmount(Integer orderType,
			Long payerMemberCode) {
		PayToAcctOrder record = new PayToAcctOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		
		return payToAcctOrderDAO.sumCurrentMonthPaymentAmount(record);
	}

	@Override
	public Integer countCurrentDayPaymentTimes(Integer orderType,
			Long payerMemberCode) {
		PayToAcctOrder record = new PayToAcctOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		return payToAcctOrderDAO.countCurrentDayPaymentTimes(record);
	}

	@Override
	public Integer countCurrentMonthPaymentTimes(Integer orderType,
			Long payerMemberCode) {
		PayToAcctOrder record = new PayToAcctOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		return payToAcctOrderDAO.countCurrentMonthPaymentTimes(record);
	}

	@Override
	public List<PayToAcctOrderDTO> getChildPayToAcctOrderList(
			Long parentOrderId, Long payerMemberCode) {
		List<PayToAcctOrderDTO> results = new ArrayList<PayToAcctOrderDTO>();
		PayToAcctOrderDTO dto = null;
		PayToAcctOrderExample example = new PayToAcctOrderExample();
		  example.createCriteria()
		  .andParentOrderIdEqualTo(parentOrderId)
		  .andPayerMemberCodeEqualTo(payerMemberCode);
		List models = payToAcctOrderDAO.selectByExample(example);
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			PayToAcctOrder model = (PayToAcctOrder)iterator.next();
			if(model!=null){
				dto = new PayToAcctOrderDTO();
				BeanUtils.copyProperties(model, dto);
				results.add(dto);
			}
		}
		return results;
	}
	
}
