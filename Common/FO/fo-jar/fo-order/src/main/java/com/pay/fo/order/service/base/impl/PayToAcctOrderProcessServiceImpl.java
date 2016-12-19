/**
 * 
 */
package com.pay.fo.order.service.base.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.base.OrderInfoService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.base.PayToAcctOrderService;

/**
 * @author NEW
 *
 */
public class PayToAcctOrderProcessServiceImpl implements PayToAcctOrderProcessService {
	
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * 付款到账户订单服务对象
	 */
	private PayToAcctOrderService payToAcctOrderService;
	/**
	 * 订单概要信息服务对象
	 */
	private OrderInfoService orderInfoService;
	

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#createOrderRdTx(com.pay.fo.order.dto.base.PayToAcctOrderDTO)
	 */
	@Override
	public Long createOrderRnTx(PayToAcctOrderDTO pay2AcctOrder) throws AppException {
		Long orderId = null;
		try{
			pay2AcctOrder.setUpdateDate(pay2AcctOrder.getCreateDate());
			orderId = payToAcctOrderService.createOrder(pay2AcctOrder);
			if(null==orderId){
				throw new RuntimeException("未获取到付款到账户订单号");
			}
			OrderInfoDTO order = new OrderInfoDTO();
		    order.setOrderId(orderId);
		    order.setOrderType(pay2AcctOrder.getOrderType());
		    order.setOrderStatus(pay2AcctOrder.getOrderStatus());
		    order.setTradeType(pay2AcctOrder.getTradeType());
		    order.setTradeAlias(pay2AcctOrder.getTradeAlias());
		  
		    order.setPayerLoginName(pay2AcctOrder.getPayerLoginName());
		    order.setPayerMemberCode(pay2AcctOrder.getPayerMemberCode());
		    order.setPayerName(pay2AcctOrder.getPayerName());

		    order.setPayeeName(pay2AcctOrder.getPayeeName());
		    order.setPayeeLoginName(pay2AcctOrder.getPayeeLoginName());
		    order.setPayeeMemberCode(pay2AcctOrder.getPayeeMemberCode());
		  
		    order.setOrderAmount(pay2AcctOrder.getOrderAmount());
		    order.setIsPayerPayFee(1);
		    if(null != pay2AcctOrder.getPayerFee()){
		    	order.setFee(pay2AcctOrder.getPayerFee());
		    }else{
		    	order.setFee(0L);
		    }
		    
		    order.setRealoutAmount(pay2AcctOrder.getOrderAmount());
		    order.setRealpayAmount(pay2AcctOrder.getOrderAmount());
		  
		    order.setCreateDate(pay2AcctOrder.getCreateDate());
		    order.setUpdateDate(pay2AcctOrder.getUpdateDate());
		    order.setPaymentReason(pay2AcctOrder.getPaymentReason());
		  
		    orderInfoService.createOrder(order);
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			throw new AppException("存储付款到账户订单、概要订单信息异常");
		}
		return orderId;
	}


	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#updateOrderStatusRdTx(com.pay.fo.order.dto.base.PayToAcctOrderDTO, int)
	 */
	@Override
	public boolean updateOrderStatusRdTx(PayToAcctOrderDTO order, int oldStatus) throws AppException {
		try{
			PayToAcctOrderDTO upOrder = new PayToAcctOrderDTO();
		    upOrder.setOrderId(order.getOrderId());
		    upOrder.setOrderStatus(order.getOrderStatus());
		    upOrder.setFailedReason(order.getFailedReason());
		    upOrder.setUpdateDate(order.getUpdateDate());
		    
		    
		    if(payToAcctOrderService.updateOrderStatus(upOrder, oldStatus)){
		    	OrderInfoDTO upOrderInfo = new OrderInfoDTO();
		    	upOrderInfo.setOrderId(order.getOrderId());
		    	upOrderInfo.setOrderStatus(order.getOrderStatus());
		    	upOrderInfo.setFailedReason(order.getFailedReason());
		    	upOrderInfo.setUpdateDate(order.getUpdateDate());
		    	if(!orderInfoService.updateOrderStatus(upOrderInfo, oldStatus)){
		    		throw new RuntimeException("更新订单概要信息状态异常");
		    	}
		    	return true;
		    }else{
	    		throw new RuntimeException("更新付款到账户订单状态异常");
		    }
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			throw new AppException("更新付款到账户订单、概要订单信息异常");
		}
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#getOrder(java.lang.Long)
	 */
	@Override
	public PayToAcctOrderDTO getOrder(Long orderId) {
		return (PayToAcctOrderDTO) payToAcctOrderService.getOrder(orderId);
	}

	/**
	 * @param payToAcctOrderService the payToAcctOrderService to set
	 */
	public void setPayToAcctOrderService(PayToAcctOrderService payToAcctOrderService) {
		this.payToAcctOrderService = payToAcctOrderService;
	}

	/**
	 * @param orderInfoService the orderInfoService to set
	 */
	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}
	
	

}
