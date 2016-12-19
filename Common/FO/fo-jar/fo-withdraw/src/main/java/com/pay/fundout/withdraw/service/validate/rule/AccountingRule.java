/**
 *  File: AccountingAction.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-7   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.rule;

import java.util.Date;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.rule.MessageRule;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;
import com.pay.poss.service.withdraw.orderafterproc.impl.OrderAfterFailProcAlertHandler;

/**
 * 提现-记账
 * @author Sandy_Yang
 */
public class AccountingRule extends MessageRule {
	
	private OrderAfterProcService orderAfterProcService;
	
	private OrderCallBackService orderCallBackService;
	
	private AccountingService accountingService;
	private WithdrawOrderService withdrawOrderService;
	
	//更新余额失败记录报警日志
	private OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler;
	
	public void setOrderAfterFailProcAlertHandler(
			OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler) {
		this.orderAfterFailProcAlertHandler = orderAfterFailProcAlertHandler;
	}
	
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withdrawRequest = (WithdrawRequestDTO)validateBean;
		HandlerParam handlerParam = new HandlerParam();
		//这里只传进了个人.企业和个人记账规则相同.
		handlerParam.setWithdrawBusinessType(WithdrawBusinessType.WITHDRAW_REQ_PERSON.getBusinessType());
		handlerParam.setOrderStatus(Integer.valueOf(withdrawRequest.getStatus().toString()));
		handlerParam.setBaseOrderDto(withdrawRequest);
		
		WithdrawOrderAppDTO withdrawOrderAppDTO = withdrawOrderService.getWithdrawOrder(withdrawRequest.getSeqId());
		//update terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(withdrawOrderAppDTO.getAmount());
		accountingDto.setBankCode(withdrawOrderAppDTO.getWithdrawBankCode());
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setOrderAmount(withdrawOrderAppDTO.getOrderAmount());
		accountingDto.setOrderId(withdrawOrderAppDTO.getSequenceId());
		accountingDto.setPayer(withdrawOrderAppDTO.getMemberCode());
		accountingDto.setPayerFee(Math.abs(withdrawOrderAppDTO.getFee()));
		handlerParam.setAccountingDto(accountingDto);
		boolean flage = orderAfterProcService.process(handlerParam, orderCallBackService, accountingService);
		if(!flage){
			withdrawRequest.setMessageId(null);
			OrderFailProcAlertModel result = new OrderFailProcAlertModel();
			result.setOrderSeq(withdrawRequest.getSeqId());
			result.setOrderStatus(101);
			result.setFailReason("更新余额失败");
			result.setAppFrom("提现");
			result.setUpdateTime(new Date());
			orderAfterFailProcAlertHandler.execute(result);
		}
		return flage;
	}
	
	
	public void setOrderAfterProcService(
			final OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}

	public void setOrderCallBackService(
			final OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setAccountingService(final AccountingService accountingService) {
		this.accountingService = accountingService;
	}
}
