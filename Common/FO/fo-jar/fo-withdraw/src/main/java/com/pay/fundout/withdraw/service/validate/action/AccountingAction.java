/**
 *  File: AccountingAction.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-7   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.action;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.rule.AbstractAction;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

/**
 * 提现-记账
 * @author Sandy_Yang
 */
public class AccountingAction extends AbstractAction {
	
	private OrderAfterProcService orderAfterProcService;
	private OrderCallBackService orderCallBackService;
	private AccountingService accountingService;
	private WithdrawOrderService withdrawOrderService;
	
	@Override
	protected void doExecute(Object validateBean) throws Exception {
		
		WithdrawRequestDTO withdrawRequest = (WithdrawRequestDTO)validateBean;
		
		WithdrawOrderAppDTO withdrawOrderAppDTO = withdrawOrderService.getWithdrawOrder(withdrawRequest.getSeqId());
		
		HandlerParam handlerParam = new HandlerParam();
		//这里只传进了个人.企业和个人记账规则相同.
		handlerParam.setWithdrawBusinessType(WithdrawBusinessType.WITHDRAW_REQ_PERSON.getBusinessType());
		handlerParam.setOrderStatus(Integer.valueOf(withdrawRequest.getStatus().toString()));
		handlerParam.setBaseOrderDto(withdrawRequest);
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
		orderAfterProcService.process(handlerParam, orderCallBackService, accountingService);
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

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
}
