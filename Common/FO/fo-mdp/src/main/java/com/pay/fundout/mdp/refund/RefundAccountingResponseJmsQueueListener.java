/** @Description 
 * @project 	poss-reconcile
 * @file 		RefundResponseJmsQueueListener.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-09-01		Sunsea_Li		Create 
 */
package com.pay.fundout.mdp.refund;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;

import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.util.JSonUtil;

/**
 * 此类用来监听与处理 记账请求
 * 
 * @author sunsea_li 2010-09-02
 */
public class RefundAccountingResponseJmsQueueListener implements MessageListener {
	private OrderCallBackService orderCallBackService; // 充退请求处理记账完成后回调服务
	private AccountingService accountingServiceSucc; // 充退请求处理成功记账服务
	private AccountingService accountingServiceFail; // 充退请求处理失败记账服务

	public void setOrderCallBackService(OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setAccountingServiceSucc(AccountingService accountingServiceSucc) {
		this.accountingServiceSucc = accountingServiceSucc;
	}

	public void setAccountingServiceFail(AccountingService accountingServiceFail) {
		this.accountingServiceFail = accountingServiceFail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		try {
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				Notify2QueueRequest request =  (Notify2QueueRequest) msg.getObject();
				String jsonString = (String) request.getTargetObject();
				// 此处添加业务处理方法-------------
				RefundOrderM mDto = JSonUtil.toObject(jsonString, new RefundOrderM().getClass());
				LogUtil.info(RefundAccountingResponseJmsQueueListener.class, "充退消息处理", OPSTATUS.START, "onMessage(Message message)", 
						"充退订单号:"+mDto.getDetailKy()+"");
				this.processAccounting(mDto);
				LogUtil.info(RefundAccountingResponseJmsQueueListener.class, "充退消息处理", OPSTATUS.SUCCESS, "onMessage(Message message)", 
						"充退订单号:"+mDto.getDetailKy()+"");
			}
		} catch (Exception e) {
			LogUtil.error(RefundAccountingResponseJmsQueueListener.class, "充退消息处理!", OPSTATUS.EXCEPTION,
					"onMessage(Message message)", "", e.getMessage(), "", "余额更新失败！");
			e.printStackTrace();
			throw new RuntimeException(e);//此处抛出异常是为了补单用
		}
	}

	/**
	 * @param mDto
	 */
	private void processAccounting(RefundOrderM mDto) throws PossException {
		HandlerParam param = new HandlerParam();
		param.setBaseOrderDto(mDto);
		param.setOrderStatus(mDto.getStatus());// 订单状态
		
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(mDto.getApplyAmount());
		accountingDto.setOrderAmount(mDto.getApplyAmount());
		accountingDto.setBankCode(mDto.getBankCode());
		//accountingDto.setBusinessType(PayForEnum.INCOME_BACK.getCode());
		accountingDto.setOrderId(mDto.getDetailKy());
		accountingDto.setPayee(mDto.getMemberCode());
		accountingDto.setBankCode(mDto.getBankCode());
		param.setAccountingDto(accountingDto);
		if (WithdrawOrderStatus.SUCCESS.getValue() == mDto.getStatus().intValue()) {
			param.setWithdrawBusinessType(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_SUCC_PERSON.getBusinessType());//
			orderCallBackService.processOrderRdTx(param, accountingServiceSucc);
		} else if (WithdrawOrderStatus.FAIL.getValue() == mDto.getStatus().intValue()) {
			param.setWithdrawBusinessType(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_FAIL_PERSON.getBusinessType());//
			orderCallBackService.processOrderRdTx(param, accountingServiceFail);
		}
	}
}
