/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentTotalInfo;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderProcessService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2AcctOrderServiceImpl implements BatchPay2AcctOrderService {
	
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
	 * 批量付款订单服务类
	 */
	private BatchPaymentOrderProcessService batchPaymentOrderProcessService;
	
	/**
	 * 付款到账户订单处理服务类
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;
	
	private BatchPaymentOrderService batchPaymentOrderService;
	
	/**
	 * 消息通知服务类
	 */
	private NotifyFacadeService notifyFacadeService;
	/**
	 * 商户通知队列名
	 */
	private String queueName;
	
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
			order.setProcessType(OrderProcessType.COMMON_BATCHPAY2ACCT_REQ);
			orderAfterProcessService.process(order, orderCallbackService, reqAccountingService);
		}catch(Throwable e){
			LogUtil.error(this.getClass(), "批量付款到账户", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "存储订单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean updateOrderStatus(BatchPaymentOrderDTO order, int oldStatus) {
		try {
			return batchPaymentOrderProcessService.updateOrderStatusRdTx(order, oldStatus);
		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "批量付款到账户", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "更新订单状态发生异常");
			log.error(e.getMessage(),e);
			return false;
		}
	}

	@Override
	public void foProcessSuccess(long orderId) {
		PayToAcctOrderDTO foOrder = payToAcctOrderProcessService.getOrder(orderId);
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_BATCHPAY2ACCT_SUCCESS);
		orderAfterProcessService.process(foOrder, orderCallbackService, successAccountingService);
		
	}
	
	@Override
	public void foProcessSuccess(PayToAcctOrderDTO foOrder) {

		foOrder.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_BATCHPAY2ACCT_SUCCESS);
		orderAfterProcessService.process(foOrder, orderCallbackService,
				successAccountingService);
	}

	@Override
	public void foProcessFail(long orderId) {
		PayToAcctOrderDTO foOrder = payToAcctOrderProcessService.getOrder(orderId);
		if(foOrder==null){
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_FAILURE.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_BATCHPAY2ACCT_FAIL);
		orderAfterProcessService.process(foOrder, orderCallbackService, failAccountingService);
		
	}
	
	@Override
	public void processCompleteBatchPay2AcctOrder() {
		List batchPaymentOrders = batchPaymentOrderService.getCompleteBatchPay2AcctOrders();
		for (Iterator iterator = batchPaymentOrders.iterator(); iterator
				.hasNext();) {
			BatchPaymentOrder order = (BatchPaymentOrder) iterator.next();
			try {
				BatchPaymentTotalInfo info = batchPaymentOrderService
						.getTotalComplateBatchPay2AcctOrder(order.getOrderId());
				BatchPaymentOrderDTO dto = new BatchPaymentOrderDTO();
				dto.setOrderId(order.getOrderId());
				dto.setUpdateDate(new Date());
				dto.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
				dto.setSuccessAmount(info.getSuccessAmount());
				dto.setSuccessCount(info.getSuccessCount());
				dto.setSuccessFee(info.getSuccessFee());
				batchPaymentOrderService.updateOrder(dto);
				
				//通知API商户
				try {
					if (OrderSmallType.API_BATCHPAY2ACCT.getValue().equals(
							order.getOrderSmallType())) {
						notifyFacadeService.sendRequest(buildRequestForMerchant(order));
					}
				} catch (Throwable e) {
					log.error("BatchPay2AcctOrderServiceImpl批量付款到账户通知商户出错!", e);
				}
				
			} catch (Exception e) {
				LogUtil.error(getClass(), e.toString(), OPSTATUS.EXCEPTION, "",
						"orderId:" + order.getOrderId(), e.getMessage(), "",
						"更新批量付款到账户完成状态发生异常");
			}
		}
	}
	
	/**
	 * 构建消息
	 * @param serialNo
	 * @return
	 */
	private Notify2QueueRequest buildRequestForMerchant(Object object) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		try {
			String jsonStr = JSonUtil.toJSonString(object);
			request.setQueueName(queueName);
			request.setTargetObject(jsonStr);
		}catch (Exception e) {
			log.error("付款API通知商户出错(消息队列)",e);
		}
		return request;
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
	 * @param batchPaymentOrderProcessService the batchPaymentOrderProcessService to set
	 */
	public void setBatchPaymentOrderProcessService(
			BatchPaymentOrderProcessService batchPaymentOrderProcessService) {
		this.batchPaymentOrderProcessService = batchPaymentOrderProcessService;
	}

	/**
	 * @param payToAcctOrderProcessService the payToAcctOrderProcessService to set
	 */
	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}
	
	/**
	 * @param notifyFacadeService the notifyFacadeService to set
	 */
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}
	
	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
