package com.pay.fo.order.service.fundoutrefund.impl;

import java.util.Date;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.FundoutRefundOrderDTO;
import com.pay.fo.order.service.base.FundoutRefundOrderService;
import com.pay.fo.order.service.fundoutprocess.FundoutProcessFactoryService;
import com.pay.fo.order.service.fundoutrefund.FundoutRefundOrderProcessService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class FundoutRefundOrderProcessServiceImpl implements FundoutRefundOrderProcessService {
	
	private FundoutRefundOrderService fundoutRefundOrderService;
	
	private FundoutProcessFactoryService fundoutProcessFactoryService;
	
	

	@Override
	public void createOrderRnTx(FundoutRefundOrderDTO order) throws AppException {
		try{
			fundoutRefundOrderService.createOrder(order);
		}catch(Throwable e){
			LogUtil.error(this.getClass(), "退票", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "创建退票订单发生异常");
			throw new AppException(e);
		}
	}

	@Override
	public void auditPassRequestRdTx(Long orderId, String auditor,
			String auditRemark) throws AppException {
		try{
			FundoutRefundOrderDTO order = (FundoutRefundOrderDTO)fundoutRefundOrderService.getOrder(orderId);
			if(order==null){
				throw new RuntimeException("无效的订单");
			}
			order.setAuditor(auditor);
			order.setAuditRemark(auditRemark);
			order.setUpdateDate(new Date());
			order.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
			if(fundoutRefundOrderService.updateOrderStatus(order, OrderStatus.INIT.getValue())){
				fundoutProcessFactoryService.refundOrder(order.getSrcOrderId(),order.getOrderId(), order.getRefundReason());
			}
		}catch(Throwable e){
			LogUtil.error(this.getClass(), "退票", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "复核通过时发生异常");
			throw new AppException(e);
		}

	}

	@Override
	public void auditRejectRequestRdTx(Long orderId, String auditor,
			String auditRemark) throws AppException {
		try{
			FundoutRefundOrderDTO order = (FundoutRefundOrderDTO)fundoutRefundOrderService.getOrder(orderId);
			if(order==null){
				throw new RuntimeException("无效的订单");
			}
			order.setAuditor(auditor);
			order.setAuditRemark(auditRemark);
			order.setUpdateDate(new Date());
			order.setUniqueKey(order.getSrcOrderId()+""+orderId);
			order.setOrderStatus(OrderStatus.PROCESSED_FAILURE.getValue());
			fundoutRefundOrderService.updateOrderStatus(order, OrderStatus.INIT.getValue());
		}catch(Throwable e){
			LogUtil.error(this.getClass(), "退票", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "复核拒绝时发生异常");
			throw new AppException(e);
		}

	}

	/**
	 * @param fundoutRefundOrderService the fundoutRefundOrderService to set
	 */
	public void setFundoutRefundOrderService(
			FundoutRefundOrderService fundoutRefundOrderService) {
		this.fundoutRefundOrderService = fundoutRefundOrderService;
	}

	/**
	 * @param fundoutProcessFactoryService the fundoutProcessFactoryService to set
	 */
	public void setFundoutProcessFactoryService(
			FundoutProcessFactoryService fundoutProcessFactoryService) {
		this.fundoutProcessFactoryService = fundoutProcessFactoryService;
	}
	
	
	

}
