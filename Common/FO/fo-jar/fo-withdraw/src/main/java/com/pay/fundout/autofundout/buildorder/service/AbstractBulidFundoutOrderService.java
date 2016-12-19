/**
 *  File: AbstractBulidFundoutOrderService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-14      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.autofundout.buildorder.service;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.fundout.autofundout.message.MessageService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

/**
 * @author Jason_wang
 *
 */
public abstract class AbstractBulidFundoutOrderService implements BulidFundoutOrderService{

	protected AccountQueryService accountQueryService;	//账号查询
	
	protected BankCardBindService bankCardBindService;	//银行卡信息查询服务
	
	protected WithdrawOrderService withdrawOrderService;	//订单服务
	
	protected OrderAfterProcService orderAfterProcService; //订单生成后处理服务
	
	protected OrderCallBackService orderCallBackService;	//回调服务
	
	protected AccountingService accountingService;	//记账服务
	
	//算费服务
	protected AccountingService accountingFeeService;
	
	protected MessageService messageService;	//邮件发送服务
	
	protected MemberQueryService memberQueryService;	//会员信息查询服务

	//通知消息服务
	protected NotifyFacadeService notifyFacadeService;
	//队列名称
	protected String queueName;
	//set注入
	public void setQueueName(final String param) {
		this.queueName = param;
	}
	//set注入
	public void setNotifyFacadeService(final NotifyFacadeService param) {
		this.notifyFacadeService = param;
	}
	public void setAccountingFeeService(AccountingService accountingFeeService) {
		this.accountingFeeService = accountingFeeService;
	}
	
	/**
	 * @param memberQueryService the memberQueryService to set
	 */
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
	
	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * @param bankCardBindService the bankCardBindService to set
	 */
	public void setBankCardBindService(BankCardBindService bankCardBindService) {
		this.bankCardBindService = bankCardBindService;
	}
	
	/**
	 * @param accountingService the accountingService to set
	 */
	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}
	
	/**
	 * @param orderCallBackService the orderCallBackService to set
	 */
	public void setOrderCallBackService(OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}
	
	/**
	 * @param orderAfterProcService the orderAfterProcService to set
	 */
	public void setOrderAfterProcService(OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}
	
	/**
	 * @param withdrawOrderService the withdrawOrderService to set
	 */
	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
	
	/**
	 * @param accountQueryService the accountQueryService to set
	 */
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
}
