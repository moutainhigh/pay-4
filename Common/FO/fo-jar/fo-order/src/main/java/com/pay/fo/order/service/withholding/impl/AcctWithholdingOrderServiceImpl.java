/**
 * 
 */
package com.pay.fo.order.service.withholding.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.withholding.AcctWithholdingOrderService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * @author NEW
 *
 */
public class AcctWithholdingOrderServiceImpl implements
		AcctWithholdingOrderService {
	
	
protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * 付款到账户订单处理服务对象
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;
	
	
	/**
	 * 订单后处理服务类
	 */
	private OrderAfterProcessService orderAfterProcessService;
	/**
	 * 订单回调服务类(更新订单状态、构建记账对象、发送通知)
	 */
	private OrderCallbackService orderCallbackService;
	
	
	/**
	 * 成功出款记账服务类（调用MA更新余额）
	 */
	private AccountingService successAccountingService;

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.withholding.Pay2AcctWithholdingService#createOrder(com.pay.fo.order.dto.base.PayToAcctOrderDTO)
	 */
	@Override
	public void createOrder(PayToAcctOrderDTO order) {
		try {
			Long orderId = payToAcctOrderProcessService.createOrderRnTx(order);
			order.setOrderId(orderId);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "账户代扣", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "存储订单信息发生异常");
			throw new RuntimeException(e);
		}

	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.withholding.Pay2AcctWithholdingService#updateOrderStatus(com.pay.fo.order.dto.base.PayToAcctOrderDTO, int)
	 */
	@Override
	public boolean updateOrderStatus(PayToAcctOrderDTO order, int oldStatus) {
		try {
			return payToAcctOrderProcessService.updateOrderStatusRdTx(order, oldStatus);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "账户代扣", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "更新订单状态发生异常");
			log.error(e.getMessage(),e);
			return false;
		}
	}

	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}

	public void setOrderAfterProcessService(
			OrderAfterProcessService orderAfterProcessService) {
		this.orderAfterProcessService = orderAfterProcessService;
	}

	public void setOrderCallbackService(OrderCallbackService orderCallbackService) {
		this.orderCallbackService = orderCallbackService;
	}

	public void setSuccessAccountingService(
			AccountingService successAccountingService) {
		this.successAccountingService = successAccountingService;
	}

	@Override
	public void paymentOrder(PayToAcctOrderDTO order) {
		try {
			order.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
			order.setUpdateDate(new Date());
			order.setProcessType(OrderProcessType.WITHHOLDING_PAY2ACCT_SUCCESS);
			orderAfterProcessService.process(order, orderCallbackService, successAccountingService);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "账户代扣", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "更新余额发生异常");
			throw new RuntimeException(e);
		}

		
	}

	
	
}
