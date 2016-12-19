package com.pay.fo.order.service.enforcewithdraw.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.enforcewithdraw.EnforceWithdrawService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * 强制提现
 * <p>
 * </p>
 * 
 * @author NEW
 * @since 2011-7-25
 * @see
 */
public class EnforceWithdrawServiceImpl implements EnforceWithdrawService {

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
			order.setProcessType(OrderProcessType.FORCE_WITHDRAW_REQ);
			orderAfterProcessService.process(order, orderCallbackService, reqAccountingService);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "强制提现", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "存储订单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void foProcessSuccess(FundoutOrderDTO foOrder) {
		if (foOrder == null) {
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.FORCE_WITHDRAW_SUCCESS);
		orderAfterProcessService.process(foOrder, orderCallbackService, successAccountingService);
	}

	@Override
	public void foProcessFail(FundoutOrderDTO foOrder) {
		if (foOrder == null) {
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_FAILURE.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.FORCE_WITHDRAW_FAIL);
		orderAfterProcessService.process(foOrder, orderCallbackService, failAccountingService);

	}

	@Override
	public void refundOrder(FundoutOrderDTO foOrder) {
		if (foOrder == null) {
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.REFUND_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.FORCE_WITHDRAW_REFUND);
		orderAfterProcessService.process(foOrder, orderCallbackService, refundAccountingService);

	}

	public void setFundoutOrderProcessService(FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}

	public void setOrderAfterProcessService(OrderAfterProcessService orderAfterProcessService) {
		this.orderAfterProcessService = orderAfterProcessService;
	}

	public void setOrderCallbackService(OrderCallbackService orderCallbackService) {
		this.orderCallbackService = orderCallbackService;
	}

	public void setReqAccountingService(AccountingService reqAccountingService) {
		this.reqAccountingService = reqAccountingService;
	}

	public void setSuccessAccountingService(AccountingService successAccountingService) {
		this.successAccountingService = successAccountingService;
	}

	public void setFailAccountingService(AccountingService failAccountingService) {
		this.failAccountingService = failAccountingService;
	}

	public void setRefundAccountingService(AccountingService refundAccountingService) {
		this.refundAccountingService = refundAccountingService;
	}

}
