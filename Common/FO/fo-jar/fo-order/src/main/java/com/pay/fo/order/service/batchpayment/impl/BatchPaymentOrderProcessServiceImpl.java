/**
 * 
 */
package com.pay.fo.order.service.batchpayment.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderProcessService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;

/**
 * @author NEW
 *
 */
public class BatchPaymentOrderProcessServiceImpl implements	BatchPaymentOrderProcessService {

	
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * 出款订单服务对象
	 */
	private BatchPaymentOrderService batchPaymentOrderService;
	
	
	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#createOrderRdTx(com.pay.fo.order.dto.base.BatchPaymentOrderDTO)
	 */
	@Override
	public Long createOrderRnTx(BatchPaymentOrderDTO foOrder) throws AppException {
		Long orderId = null;
		try{
			foOrder.setUpdateDate(foOrder.getCreateDate());
			orderId = batchPaymentOrderService.createOrder(foOrder);
			if(null==orderId){
				log.error("未获取到出款订单号");
				throw new RuntimeException();
			}
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			throw new AppException("存储批量付款订单异常");
		}
		return orderId;
	}


	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#updateOrderStatusRdTx(com.pay.fo.order.dto.base.BatchPaymentOrderDTO, int)
	 */
	@Override
	public boolean updateOrderStatusRdTx(BatchPaymentOrderDTO order, int oldStatus) throws AppException {
		try{
			BatchPaymentOrderDTO upOrder = new BatchPaymentOrderDTO();
		    upOrder.setOrderId(order.getOrderId());
		    upOrder.setOrderStatus(order.getOrderStatus());
		    upOrder.setUpdateDate(order.getUpdateDate());
		    upOrder.setSuccessAmount(order.getSuccessAmount());
		    upOrder.setSuccessCount(order.getSuccessCount());
		    upOrder.setSuccessFee(order.getSuccessFee());
		    
		    if(!batchPaymentOrderService.updateOrderStatus(upOrder, oldStatus)){
		    		log.error("更新批量付款订单状态异常");
		    		throw new RuntimeException();
		    	
		    }
		    return true;
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			throw new AppException("存储批量付款订单异常");
		}
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#getOrder(java.lang.Long)
	 */
	@Override
	public BatchPaymentOrderDTO getOrder(Long orderId) {
		return (BatchPaymentOrderDTO) batchPaymentOrderService.getOrder(orderId);
	}
	
	
	@Override
	public BatchPaymentOrderDTO getOrder(Long memberCode, Integer orderType,
			String businessBatchNo) {
		return batchPaymentOrderService.getOrder(memberCode, orderType, businessBatchNo);
	}

	/**
	 * @param batchPaymentOrderService the batchPaymentOrderService to set
	 */
	public void setBatchPaymentOrderService(BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}


	

}
