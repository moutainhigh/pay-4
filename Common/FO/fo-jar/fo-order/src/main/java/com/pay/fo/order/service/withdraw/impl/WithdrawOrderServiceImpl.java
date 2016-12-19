/**
 * 
 */
package com.pay.fo.order.service.withdraw.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.withdraw.WithdrawOrderService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * @author NEW
 *
 */
public class WithdrawOrderServiceImpl implements WithdrawOrderService {

	protected Log log = LogFactory.getLog(getClass());
		
	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;
	/**
	 * 订单后处理服务类
	 */
	private OrderAfterProcessService orderAfterProcessService;
	/**
	 * 订单回调服务类(更新订单状态、构建记账对象、发送通知)
	 */
	private OrderCallbackService orderCallbackService;
	
	/**
	 * 申请记账服务类（调用MA更新余额）
	 */
	private AccountingService reqAccountingService;
	
	/**
	 * 成功出款记账服务类（调用MA更新余额）
	 */
	private AccountingService successAccountingService;
	
	/**
	 * 出款失败记账服务类（调用MA更新余额）
	 */
	private AccountingService failAccountingService;
	
	
	/**
	 * 退票记账服务类（调用MA更新余额）
	 *
	 */
	 private AccountingService refundAccountingService;


	@Override
	public void createOrder(FundoutOrderDTO order) {
		try {
			Long orderId = fundoutOrderProcessService.createOrderRnTx(order);
			order.setOrderId(orderId);
			order.setOrderStatus(OrderStatus.PROCESSING.getValue());
			order.setUpdateDate(new Date());
			order.setProcessType(OrderProcessType.COMMON_WITHDRAW_REQ);
			orderAfterProcessService.process(order, orderCallbackService, reqAccountingService);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "提现", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "存储订单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}
		
	}



	@Override
	public boolean updateOrderStatus(FundoutOrderDTO order, int oldStatus) {
		try {
			return fundoutOrderProcessService.updateOrderStatusRdTx(order, oldStatus);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "提现", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "更新订单状态发生异常");
			log.error(e.getMessage(),e);
			return false;
		}
		
	}

	

	/**
	 * @param fundoutOrderProcessService the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}



	
	@Override
	public void foProcessSuccess(FundoutOrderDTO foOrder) {
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_WITHDRAW_SUCCESS);
		orderAfterProcessService.process(foOrder, orderCallbackService, successAccountingService);
	}



	@Override
	public void foProcessFail(FundoutOrderDTO foOrder) {
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_FAILURE.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_WITHDRAW_FAIL);
		orderAfterProcessService.process(foOrder, orderCallbackService, failAccountingService);
		
	}



	@Override
	public void refundOrder(FundoutOrderDTO foOrder) {
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.REFUND_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_WITHDRAW_REFUND);
		orderAfterProcessService.process(foOrder, orderCallbackService, refundAccountingService);
		
	}



	/**
	 * @param orderAfterProcessService the orderAfterProcessService to set
	 */
	public void setOrderAfterProcessService(
			OrderAfterProcessService orderAfterProcessService) {
		this.orderAfterProcessService = orderAfterProcessService;
	}



	/**
	 * @param orderCallbackService the orderCallbackService to set
	 */
	public void setOrderCallbackService(OrderCallbackService orderCallbackService) {
		this.orderCallbackService = orderCallbackService;
	}



	/**
	 * @param reqAccountingService the reqAccountingService to set
	 */
	public void setReqAccountingService(AccountingService reqAccountingService) {
		this.reqAccountingService = reqAccountingService;
	}



	/**
	 * @param successAccountingService the successAccountingService to set
	 */
	public void setSuccessAccountingService(
			AccountingService successAccountingService) {
		this.successAccountingService = successAccountingService;
	}



	/**
	 * @param failAccountingService the failAccountingService to set
	 */
	public void setFailAccountingService(AccountingService failAccountingService) {
		this.failAccountingService = failAccountingService;
	}



	/**
	 * @param refundAccountingService the refundAccountingService to set
	 */
	public void setRefundAccountingService(AccountingService refundAccountingService) {
		this.refundAccountingService = refundAccountingService;
	}




}
