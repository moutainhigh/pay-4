/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderProcessService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * @author NEW
 *
 */
public class BatchPay2BankOrderServiceImpl implements BatchPay2BankOrderService {
	
	protected Log log = LogFactory.getLog(getClass());
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
	
	/**
	 * 批量付款订单服务类
	 */
	private BatchPaymentOrderProcessService batchPaymentOrderProcessService;
	
	@Override
	public void createOrder(BatchPaymentOrderDTO order) {
		try{
			BatchPaymentOrderDTO _order = batchPaymentOrderProcessService.getOrder(order.getPayerMemberCode(), order.getOrderType(), order.getBusinessBatchNo());
			if(_order==null){
				Long orderId = batchPaymentOrderProcessService.createOrderRnTx(order);
				order.setOrderId(orderId);
			}else{
				order = _order;
			}
			
			order.setUpdateDate(new Date());
			order.setOrderStatus(OrderStatus.PROCESSING.getValue());
			if(OrderSmallType.AUTO_BATCHPAY2BANK.getValue().equals(order.getOrderSmallType())){
				order.setProcessType(OrderProcessType.AUTO_BATCHPAY2BANK_REQ);
			}else{
				order.setProcessType(OrderProcessType.COMMON_BATCHPAY2BANK_REQ);
			}
			orderAfterProcessService.process(order, orderCallbackService, reqAccountingService);
		}catch(Throwable e){
			LogUtil.error(this.getClass(), "批量付款到银行", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "存储订单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}

	}
	

	@Override
	public boolean updateOrderStatus(BatchPaymentOrderDTO order, int oldStatus) {
		try {
			return batchPaymentOrderProcessService.updateOrderStatusRdTx(order, oldStatus);
		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "批量付款到银行", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "更新订单状态发生异常");
			log.error(e.getMessage(),e);
			return false;
		}
	}

	
	@Override
	public void foProcessSuccess(FundoutOrderDTO foOrder) {
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		if(OrderSmallType.AUTO_BATCHPAY2BANK.getValue().equals(foOrder.getOrderSmallType())){
			foOrder.setProcessType(OrderProcessType.AUTO_BATCHPAY2BANK_SUCCESS);
		}else{
			foOrder.setProcessType(OrderProcessType.COMMON_BATCHPAY2BANK_SUCCESS);
		}
		
		orderAfterProcessService.process(foOrder, orderCallbackService, successAccountingService);
		
	}

	@Override
	public void foProcessFail(FundoutOrderDTO foOrder) {
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_FAILURE.getValue());
		foOrder.setUpdateDate(new Date());
		if(OrderSmallType.AUTO_BATCHPAY2BANK.getValue().equals(foOrder.getOrderSmallType())){
			foOrder.setProcessType(OrderProcessType.AUTO_BATCHPAY2BANK_FAIL);
		}else{
			foOrder.setProcessType(OrderProcessType.AUTO_BATCHPAY2BANK_FAIL);
		}
		
		orderAfterProcessService.process(foOrder, orderCallbackService, failAccountingService);
		
		
	}
	

	@Override
	public void refundOrder(FundoutOrderDTO foOrder) {
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.REFUND_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		if(OrderSmallType.AUTO_BATCHPAY2BANK.getValue().equals(foOrder.getOrderSmallType())){
			foOrder.setProcessType(OrderProcessType.AUTO_BATCHPAY2BANK_REFUND);
		}else{
			foOrder.setProcessType(OrderProcessType.AUTO_BATCHPAY2BANK_REFUND);
		}
		orderAfterProcessService.process(foOrder, orderCallbackService, refundAccountingService);
	}
	

	/**
	 * @param batchPaymentOrderProcessService the batchPaymentOrderProcessService to set
	 */
	public void setBatchPaymentOrderProcessService(
			BatchPaymentOrderProcessService batchPaymentOrderProcessService) {
		this.batchPaymentOrderProcessService = batchPaymentOrderProcessService;
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
