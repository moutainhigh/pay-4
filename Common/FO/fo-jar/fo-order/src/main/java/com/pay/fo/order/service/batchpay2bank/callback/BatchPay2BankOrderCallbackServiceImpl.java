/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank.callback;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.service.AbstractOrderCallbackService;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderProcessService;
import com.pay.inf.exception.AppException;
import com.pay.pe.dto.AccountingDto;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.JSonUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2BankOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {
	/**
	 * 批量付款订单处理服务
	 */
	private BatchPaymentOrderProcessService batchPaymentOrderProcessService;
	
	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;
	
	
	/**
	 * 通知生成明细订单队列名称
	 */
	private String queueName;
	
	
	
	/**
	 * @param fundoutOrderProcessService the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}

	private FundoutOrderDTO initFundoutOrder(Order order){
		if(!(order instanceof FundoutOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (FundoutOrderDTO)order;
	}
	
	private BatchPaymentOrderDTO initBatchPaymentOrder(Order order){
		if(!(order instanceof BatchPaymentOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (BatchPaymentOrderDTO)order;
	}

	@Override
	public boolean updateOrderStatus(Order order) throws AppException {
		
		if(order.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_REQ.getValue())||order.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_REQ.getValue())){
			BatchPaymentOrderDTO dto = initBatchPaymentOrder(order);
			batchPaymentOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		}else if(order.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_REFUND.getValue())||order.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_REFUND.getValue())){
			FundoutOrderDTO dto = initFundoutOrder(order);
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.PROCESSED_SUCCESS.getValue());
		}else{
			FundoutOrderDTO dto = initFundoutOrder(order);
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.PROCESSING.getValue());
		}
		return true;
	}

	@Override
	public void notify(Order order) {
		
		if(order.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_REQ.getValue())||order.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_REQ.getValue())){
			BatchPaymentOrderDTO dto = initBatchPaymentOrder(order);
			notifyMessage(dto);
		}

	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		
		
		AccountingDto acctDto = new AccountingDto();
		
		//-----------------------------
		//acctDto.setPayerCurrencyCode(CurrencyCodeEnum.CNY.getCode());
		//acctDto.setPayeeCurrencyCode(CurrencyCodeEnum.CNY.getCode());
		//acctDto.setPayerAcctType(101);
		//acctDto.setPayeeAcctType(101);
		//-----------------------------
	    
		if(order.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_REQ.getValue())||order.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_REQ.getValue())){
			System.out.println(order.getClass());
			BatchPaymentOrderDTO dto = initBatchPaymentOrder(order);

			//---added  PengJiangbo
			acctDto.setPayerAcctType(dto.getPayerAcctType());
			acctDto.setPayeeAcctType(dto.getPayeeAcctType());
			acctDto.setPayerCurrencyCode(dto.getPayerCurrencyCode());
			acctDto.setPayeeCurrencyCode(dto.getPayeeCurrencyCode());
			//---added  PengJiangbo end
			
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
		FundoutOrderDTO dto = initFundoutOrder(order);
		//---added  PengJiangbo
		acctDto.setPayerAcctType(dto.getPayerAcctType());
		acctDto.setPayeeAcctType(dto.getPayeeAcctType());
		acctDto.setPayerCurrencyCode(dto.getPayerCurrencyCode());
		acctDto.setPayeeCurrencyCode(dto.getPayeeCurrencyCode());
		//---added  PengJiangbo end
		acctDto.setAmount(dto.getRealoutAmount());
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setBankCode(String.valueOf(dto.getFundoutBankCode()));
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		if(dto.getIsPayerPayFee()==1){
			acctDto.setPayerFee(dto.getFee());
		}else{
			acctDto.setPayeeFee(dto.getFee());
		}
		if(dto.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_FAIL.getValue())||dto.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_FAIL.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_SUCCESS.getValue())||dto.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_SUCCESS.getValue())){
			
		}else if(dto.getProcessType().getValue().equals(OrderProcessType.COMMON_BATCHPAY2BANK_REFUND.getValue())||dto.getProcessType().getValue().equals(OrderProcessType.AUTO_BATCHPAY2BANK_REFUND.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
			acctDto.setOrderId(dto.getRefundOrderId());
		}
		
		return acctDto;
	}
	
	
	/**
	 * 通知生成明细订单
	 * @param dto
	 */
	private void notifyMessage(BatchPaymentOrderDTO dto){
		String jsonStr = JSonUtil.toJSonString(dto.getOrderId()+"_toBank");
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
