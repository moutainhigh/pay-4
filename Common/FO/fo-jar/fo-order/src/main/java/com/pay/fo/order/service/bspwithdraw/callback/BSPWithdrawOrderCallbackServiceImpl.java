/**
 * 
 */
package com.pay.fo.order.service.bspwithdraw.callback;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.AbstractOrderCallbackService;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.inf.exception.AppException;
import com.pay.pe.dto.AccountingDto;

/**
 * @author NEW
 *
 */
public class BSPWithdrawOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {
	
	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;
	
	
	
	/**
	 * @param fundoutOrderProcessService the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}

	private FundoutOrderDTO init(Order order){
		if(!(order instanceof FundoutOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (FundoutOrderDTO)order;
	}

	@Override
	public boolean updateOrderStatus(Order order) throws AppException {
		FundoutOrderDTO dto = init(order);
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.BSP_WITHDRAW_REQ.getValue())){
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.BSP_WITHDRAW_REFUND.getValue())){
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.PROCESSED_SUCCESS.getValue());
		}else{
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.PROCESSING.getValue());
		}
		return true;
	}

	@Override
	public void notify(Order order) {
		

	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		FundoutOrderDTO dto = init(order);
		
		AccountingDto acctDto = new AccountingDto();
		acctDto.setAmount(dto.getRealoutAmount());
		acctDto.setBankCode(String.valueOf(dto.getFundoutBankCode()));
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		if(dto.getIsPayerPayFee()==1){
			acctDto.setPayerFee(dto.getFee());
		}else{
			acctDto.setPayeeFee(dto.getFee());
		}
		
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.BSP_WITHDRAW_REQ.getValue())){
			acctDto.setPayer(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.BSP_WITHDRAW_FAIL.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.BSP_WITHDRAW_SUCCESS.getValue())){
			
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.BSP_WITHDRAW_REFUND.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
			acctDto.setOrderId(dto.getRefundOrderId());
		}
		
		return acctDto;
	}


}
