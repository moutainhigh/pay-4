/**
 * 
 */
package com.pay.fo.order.service.fundoutprocess;

import java.util.Map;

import org.springframework.util.Assert;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderProcessService;

/**
 * @author NEW
 *
 */
public class FundoutProcessFactoryServiceImpl implements FundoutProcessFactoryService {
		
	private Map<String, FundoutProcessService> processService;
	
	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;

	/**
	 * @param processService the processService to set
	 */
	public void setProcessService(final Map<String, FundoutProcessService> processService) {
		this.processService = processService;
	}
	
	

	/**
	 * @param fundoutOrderProcessService the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			final FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}



	@Override
	public void foProcessSuccess(final long orderId) {
		FundoutOrderDTO order = fundoutOrderProcessService.getOrder(orderId);
		if(order==null){
			throw new RuntimeException("无效的订单");
		}
		processService.get(order.getOrderSmallType()).foProcessSuccess(order);

	}

	@Override
	public void foProcessFail(final long orderId, final String failedReason,
			final Integer payerAcctType, final Integer payeeAcctType,
			final String payerCurrencyCode, final String payeeCurrencyCode) {
		Assert.notNull(orderId, "foProcessFail orderId is null");
		FundoutOrderDTO order = fundoutOrderProcessService.getOrder(orderId);
		if(order==null){
			throw new RuntimeException("无效的订单");
		}
		order.setFailedReason(failedReason);
		//-------add  PengJiangbo
		order.setPayerAcctType(payerAcctType);
		order.setPayeeAcctType(payeeAcctType);
		order.setPayerCurrencyCode(payerCurrencyCode);
		order.setPayeeCurrencyCode(payeeCurrencyCode);
		order.setIsPayerPayFee(0);
		//－－－－－－add  PengJiangbo end
		processService.get(order.getOrderSmallType()).foProcessFail(order);
		
	}
	
	@Override
	public void refundOrder(final Long orderId,final Long refundOrderId,final String refundReson) {
		Assert.notNull(orderId, "foProcessFail orderId is null");
		Assert.notNull(refundOrderId, "foProcessFail refundOrderId is null");
		Assert.notNull(refundReson, "foProcessFail refundReson is null");
		FundoutOrderDTO order = fundoutOrderProcessService.getOrder(orderId);
		if(order==null){
			throw new RuntimeException("无效的订单");
		}
		order.setFailedReason(refundReson);
		order.setRefundOrderId(refundOrderId);
		processService.get(order.getOrderSmallType()).refundOrder(order);


	}

}
