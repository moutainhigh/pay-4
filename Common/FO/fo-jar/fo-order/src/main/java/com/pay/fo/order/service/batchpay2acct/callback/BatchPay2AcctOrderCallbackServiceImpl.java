/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct.callback;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.service.AbstractOrderCallbackService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderProcessService;
import com.pay.inf.exception.AppException;
import com.pay.pe.dto.AccountingDto;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.util.JSonUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2AcctOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {
	/**
	 * 批量付款订单处理服务
	 */
	private BatchPaymentOrderProcessService batchPaymentOrderProcessService;
	
	/**
	 * 付款到账户订单处理服务类
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;
	
	
	/**
	 * 通知生成明细订单队列名称
	 */
	private String queueName;
	
	
	

	/**
	 * @param payToAcctOrderProcessService the payToAcctOrderProcessService to set
	 */
	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}

	private PayToAcctOrderDTO initPayToAcctOrder(Order order){
		if(!(order instanceof PayToAcctOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (PayToAcctOrderDTO)order;
	}
	
	private BatchPaymentOrderDTO initBatchPaymentOrder(Order order){
		if(!(order instanceof BatchPaymentOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (BatchPaymentOrderDTO)order;
	}

	@Override
	public boolean updateOrderStatus(Order order) throws AppException {
		
		if(order.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_BATCHPAY2ACCT_REQ.getValue())){
			BatchPaymentOrderDTO dto = initBatchPaymentOrder(order);
			batchPaymentOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		}else{
			PayToAcctOrderDTO dto = initPayToAcctOrder(order);
			payToAcctOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		}
		return true;
	}

	@Override
	public void notify(Order order) {
		
		if(order.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_BATCHPAY2ACCT_REQ.getValue())){
			BatchPaymentOrderDTO dto = initBatchPaymentOrder(order);
			notifyMessage(dto);
		}

	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		
		
		AccountingDto acctDto = new AccountingDto();
		
		if(order.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_BATCHPAY2ACCT_REQ.getValue())){
			BatchPaymentOrderDTO dto = initBatchPaymentOrder(order);
			acctDto.setAmount(dto.getRealoutAmount());
			acctDto.setOrderAmount(dto.getOrderAmount());
			acctDto.setOrderId(dto.getOrderId());
			acctDto.setHasCaculatedPrice(true);
			if(dto.getIsPayerPayFee()==1){
				acctDto.setPayerFee(dto.getFee());
			}else{
				acctDto.setPayeeFee(dto.getFee());
			}
			acctDto.setPayer(dto.getPayerMemberCode());
			return acctDto;
		}
		PayToAcctOrderDTO dto = initPayToAcctOrder(order);
		acctDto.setAmount(dto.getOrderAmount());
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		acctDto.setPayerFee(0L);
		acctDto.setPayeeFee(0L);
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_BATCHPAY2ACCT_FAIL.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_BATCHPAY2ACCT_SUCCESS.getValue())){
			acctDto.setPayee(dto.getPayeeMemberCode());
		}
		
		return acctDto;
	}
	
	/**
	 * 通知生成明细订单
	 * @param dto
	 */
	private void notifyMessage(BatchPaymentOrderDTO dto){
		String jsonStr = JSonUtil.toJSonString(dto.getOrderId()+"_toAcct");
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		//通知后台处理
		notifyFacadeService.sendRequest(request);
	}
	

	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * @param batchPaymentOrderProcessService the batchPaymentOrderProcessService to set
	 */
	public void setBatchPaymentOrderProcessService(
			BatchPaymentOrderProcessService batchPaymentOrderProcessService) {
		this.batchPaymentOrderProcessService = batchPaymentOrderProcessService;
	}
	
	

}
