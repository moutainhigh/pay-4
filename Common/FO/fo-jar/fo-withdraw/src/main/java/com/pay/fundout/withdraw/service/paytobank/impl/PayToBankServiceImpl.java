/**
 *  File: PayToBankServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-12      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.paytobank.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.paytobank.PayToBankService;
import com.pay.fundout.withdraw.service.paytobank.validate.rule.Pay2BankDTO;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;
import com.pay.poss.service.withdraw.orderafterproc.impl.OrderAfterFailProcAlertHandler;

/**
 * @author bill_peng
 * 
 */
public class PayToBankServiceImpl implements PayToBankService {

	private Pay2BankValidateService pay2BankValidateService;

	private WithdrawOrderService withdrawOrderService;

	protected OrderAfterProcService orderAfterProcService;
	// 订单回调后处理服务
	protected OrderCallBackService orderCallBackService;
	// 订单后处理算费和更新余额服务
	protected AccountingService accountingService;

	//更新余额失败记录报警日志
	private OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler;
	
	public void setOrderAfterFailProcAlertHandler(
			OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler) {
		this.orderAfterFailProcAlertHandler = orderAfterFailProcAlertHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.fundout.withdraw.service.paytobank.PayToBankService#
	 * createPayToBankOrderRdTx
	 * (com.pay.fundout.withdraw.service.paytobank.validate
	 * .rule.Pay2BankDTO)
	 */
	@Override
	public Long createPayToBankOrder(Pay2BankDTO dto) {
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();

		withdrawOrderAppDTO.setMemberCode(Long.valueOf(dto.getMemberCode()));
		MemberInfoDto mbr = pay2BankValidateService.getMemberByMemberCode(dto.getMemberCode());
		withdrawOrderAppDTO.setMemberType(new Long(mbr.getMemberType()));
		withdrawOrderAppDTO.setMemberAccType(new Long(dto.getPayerAccountType()));
		AcctAttribDto acctAttribDto = pay2BankValidateService.getAcctAttributeDto(dto.getMemberCode(), Integer.valueOf(dto.getPayerAccountType()));
		withdrawOrderAppDTO.setMemberAcc(acctAttribDto.getAcctCode());
		long amount = dto.getPaymentAmount().multiply(new BigDecimal(1000)).longValue();
		withdrawOrderAppDTO.setOrderAmount(new Long(amount));
		// withdrawOrderAppDTO.setPrioritys(Short.valueOf("5"));
		withdrawOrderAppDTO.setAccountName(dto.getPayeeName());
		withdrawOrderAppDTO.setBankAcct(dto.getPayeeBankAccount());
		// withdrawOrderAppDTO.setBankAcctType(withdrawRequest.getBankAcctType());
		withdrawOrderAppDTO.setBankKy(dto.getPayeeBankCode());
		withdrawOrderAppDTO.setOrderRemarks(dto.getPaymentReason());
		withdrawOrderAppDTO.setBankBranch(dto.getPayeeOpeningBankName());
		withdrawOrderAppDTO.setBankProvince(Short.valueOf(dto.getPayeeBankProvince()));
		withdrawOrderAppDTO.setBankCity(Short.valueOf(dto.getPayeeBankCity()));
		withdrawOrderAppDTO.setWithdrawBankCode(dto.getPayeeBankCode());// 出款银行
		withdrawOrderAppDTO.setPayeeMobile(dto.getPayeeMobile());
		long fee = toLongAmount(dto.getHandlingCharge());
		withdrawOrderAppDTO.setAmount(dto.getReceiveAmountLong());
		withdrawOrderAppDTO.setPayerAmount(dto.getPayAmountLong());
		withdrawOrderAppDTO.setFee(fee);
		// 提现类别
		long type = 0L;
		if (dto.getTradeType().intValue() == 1) {
			type = 1L;
		}
		withdrawOrderAppDTO.setType(type);
		withdrawOrderAppDTO.setTradeType(dto.getTradeType());
		withdrawOrderAppDTO.setBusiType(new Long(WithdrawOrderBusiType.PAY2BANK.getCode()));
		// 订单状态
		withdrawOrderAppDTO.setStatus(Long.valueOf(OrderStatus.INIT.getValue()));
		Long seqId = withdrawOrderService.createWithdrawOrderRnTx(withdrawOrderAppDTO);
		if (seqId != null) {
			if(processOrder(seqId)){
				return seqId;
			}else{
				OrderFailProcAlertModel result = new OrderFailProcAlertModel();
				result.setOrderSeq(seqId);
				result.setOrderStatus(101);
				result.setFailReason("更新余额失败");
				result.setAppFrom("付款到银行");
				result.setUpdateTime(new Date());
				orderAfterFailProcAlertHandler.execute(result);
			}
		}
		return null;
	}

	/**
	 * 处理订单
	 * 
	 * @param dealId
	 */
	private boolean processOrder(long dealId) {
		HandlerParam param = new HandlerParam();
		WithdrawOrderAppDTO dto = withdrawOrderService.getWithdrawOrder(dealId);
		param.setBaseOrderDto(dto);
		param.setOrderStatus(new Integer(String.valueOf(dto.getStatus())));
		param.setWithdrawBusinessType(WithdrawBusinessType.PAYTOBANK_REQ_PERSON.getBusinessType());
		
		//update by terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(dto.getAmount());
		accountingDto.setBankCode(dto.getWithdrawBankCode());
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setOrderAmount(dto.getOrderAmount());
		accountingDto.setOrderId(dto.getSequenceId());
		accountingDto.setPayer(dto.getMemberCode());
		accountingDto.setPayerFee(Math.abs(dto.getFee()));
		param.setAccountingDto(accountingDto);
		
		boolean result = orderAfterProcService.process(param, orderCallBackService, accountingService);
		
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		withdrawOrderAppDTO.setSequenceId(dto.getSequenceId());
		withdrawOrderAppDTO.setUpdateTime(new Date());
		if(result){
			withdrawOrderAppDTO.setStatus(Long.valueOf(OrderStatus.PROCESSING.getValue()));
		}else{
			withdrawOrderAppDTO.setStatus(Long.valueOf(OrderStatus.APPLICATION_FAILURE.getValue()));
		}
		withdrawOrderService.updateWithdrawOrder(withdrawOrderAppDTO);
		return result;
	}

	/**
	 * 金额转Long
	 * 
	 * @param amount
	 * @return
	 */
	protected Long toLongAmount(BigDecimal amount) {
		if (amount == null) {
			return 0L;
		}
		return amount.multiply(new BigDecimal(1000)).longValue();
	}

	public void setPay2BankValidateService(Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setOrderAfterProcService(OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}

	public void setOrderCallBackService(OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}

}
