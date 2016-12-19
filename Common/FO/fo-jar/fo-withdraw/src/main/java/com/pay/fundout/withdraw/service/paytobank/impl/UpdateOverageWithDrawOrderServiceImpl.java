package com.pay.fundout.withdraw.service.paytobank.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.paytobank.UpdateOverageWithDrawOrderService;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;
import com.pay.poss.service.withdraw.orderafterproc.impl.OrderAfterFailProcAlertHandler;
import com.pay.util.JSonUtil;

/**
 * 更新余额并更新订单状态
 */
public class UpdateOverageWithDrawOrderServiceImpl implements
		UpdateOverageWithDrawOrderService {
	private Log log = LogFactory.getLog(getClass());
	// 更新余额
	private OrderAfterProcService orderAfterProcService;

	private OrderCallBackService orderCallBackService;

	private AccountingService accountingService;

	// 提现订单服务
	private WithdrawOrderService withdrawOrderService;

	// 更新余额失败记录报警日志
	private OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler;

	// 通知消息服务
	private NotifyFacadeService notifyFacadeService;
	// 队列名称
	private String queueName;

	// set注入
	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	// set注入
	public void setQueueName(final String param) {
		this.queueName = param;
	}

	// set注入
	public void setNotifyFacadeService(final NotifyFacadeService param) {
		this.notifyFacadeService = param;
	}

	public void setOrderAfterFailProcAlertHandler(
			OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler) {
		this.orderAfterFailProcAlertHandler = orderAfterFailProcAlertHandler;
	}

	public void setOrderAfterProcService(
			final OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}

	public void setOrderCallBackService(
			final OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setAccountingService(final AccountingService accountingService) {
		this.accountingService = accountingService;
	}

	/**
	 * 更新余额并记账
	 */
	@Override
	public void updateOverage(WithdrawRequestDTO withdrawRequestDTO)
			throws Exception {

		boolean flag = process(withdrawRequestDTO);
		if (flag) {
			// 发送订单生成消息
			String jsonStr = JSonUtil.toJSonString(withdrawRequestDTO
					.getSeqId());
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr));
		} else {
			withdrawRequestDTO.setMessageId(String
					.valueOf(OrderStatus.APPLICATION_FAILURE.getValue()));
			OrderFailProcAlertModel result = new OrderFailProcAlertModel();
			result.setOrderSeq(withdrawRequestDTO.getSeqId());
			result.setOrderStatus(101);
			result.setFailReason("更新余额失败");
			result.setAppFrom("提现");
			result.setUpdateTime(new Date());
			orderAfterFailProcAlertHandler.execute(result);
		}
	}

	private boolean process(WithdrawRequestDTO withdrawRequestDTO) {
		HandlerParam handlerParam = new HandlerParam();
		// 这里只传进了个人.企业和个人记账规则相同.
		handlerParam
				.setWithdrawBusinessType(WithdrawBusinessType.WITHDRAW_REQ_PERSON
						.getBusinessType());
		handlerParam.setOrderStatus(Integer.valueOf(withdrawRequestDTO
				.getStatus().toString()));
		handlerParam.setBaseOrderDto(withdrawRequestDTO);

		WithdrawOrderAppDTO withdrawOrderAppDTO = withdrawOrderService
				.getWithdrawOrder(withdrawRequestDTO.getSeqId());
		// update terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(withdrawOrderAppDTO.getAmount());
		accountingDto.setBankCode(withdrawOrderAppDTO.getWithdrawBankCode());
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setOrderAmount(withdrawOrderAppDTO.getOrderAmount());
		accountingDto.setOrderId(withdrawOrderAppDTO.getSequenceId());
		accountingDto.setPayer(withdrawOrderAppDTO.getMemberCode());
		accountingDto.setPayerFee(Math.abs(withdrawOrderAppDTO.getFee()));
		handlerParam.setAccountingDto(accountingDto);
		boolean flag = orderAfterProcService.process(handlerParam,
				orderCallBackService, accountingService);

		WithdrawOrderAppDTO order = new WithdrawOrderAppDTO();
		order.setSequenceId(withdrawRequestDTO.getSeqId());
		order.setUpdateTime(new Date());
		if (flag) {
			order.setStatus(Long.valueOf(OrderStatus.PROCESSING.getValue()));
		} else {
			order.setStatus(Long.valueOf(OrderStatus.APPLICATION_FAILURE
					.getValue()));
		}

		// 更新订单状态
		try {
			withdrawOrderService.updateWithdrawOrder(order);
		} catch (Exception e) {
			log.error("更新订单状态失败,原订单信息 [" + order + "]", e);
		}
		return flag;
	}

	@Override
	public boolean processBspWidraw(WithdrawRequestDTO withdrawRequestDTO) {
		return process(withdrawRequestDTO);
	}

	// 构建对象产生
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}

}
