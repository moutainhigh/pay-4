/**
 *  File: BackFundMentOrderRepairServicesImpl.java
 *  Description:
 *  Copyright 2010-12-29 pay Corporation. All rights reserved.
 *  2010-12-29      Sean_yi      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.orderconsistency.BackFundMentOrderRepairService;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

public class BackFundMentOrderRepairServicesImpl implements
		BackFundMentOrderRepairService {

	private WithdrawOrderService withdrawOrderService;
	private OrderAfterProcService orderAfterService;
	private HashMap<String, OrderCallBackService> callBackServiceMap;
	private HashMap<String, AccountingService> accountingServiceMap;
	private HashMap<String, String> businessTypeMap;
	private Map<String,String> mappingToDealType;

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setOrderAfterService(OrderAfterProcService orderAfterService) {
		this.orderAfterService = orderAfterService;
	}

	public void setCallBackServiceMap(
			HashMap<String, OrderCallBackService> callBackServiceMap) {
		this.callBackServiceMap = callBackServiceMap;
	}

	public void setAccountingServiceMap(
			HashMap<String, AccountingService> accountingServiceMap) {
		this.accountingServiceMap = accountingServiceMap;
	}

	public void setMappingToDealType(Map<String, String> mappingToDealType) {
		this.mappingToDealType = mappingToDealType;
	}

	public void setBusinessTypeMap(HashMap<String, String> businessTypeMap) {
		this.businessTypeMap = businessTypeMap;
	}
	
	/**
	 * 查询没有生成退款订单的数据
	 * @return
	 */
	@Override
	public List<WithdrawOrderAppDTO> getNoBackFundMentOrderData(
			Map<String, Object> map) {
		return withdrawOrderService.getNoFundMentOrderList(map);
	}

	/**
	 * 修复没有生成退款订单数据
	 * @param dto
	 */
	@Override
	public boolean repairBackFundMentOrder(WithdrawOrderAppDTO dto) {
		if (dto == null) {
			return false;
		}
		HandlerParam param = null;
		//业务类型
		StringBuffer busineeType = new StringBuffer(dto.getBusiType().toString());
		String businessTypeVal = null;		
		// 记账--退回到会员账户
//		appDto.setStatus(Long.valueOf(112));
		param = new HandlerParam();
		param.setBaseOrderDto(dto);
		param.setOrderStatus(112);
		businessTypeVal = busineeType.append(param.getOrderStatus()).toString();
		param.setWithdrawBusinessType(businessTypeMap.get(businessTypeVal));
		//update by terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(dto.getAmount());
		accountingDto.setOrderAmount(dto.getOrderAmount());
		accountingDto.setBankCode(dto.getWithdrawBankCode());
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setOrderId(dto.getSequenceId());
		accountingDto.setPayee(dto.getMemberCode());
		accountingDto.setPayerFee(Math.abs(dto.getFee()));
		param.setAccountingDto(accountingDto);
		return this.orderAfterService.process(param, callBackServiceMap.get(businessTypeVal), accountingServiceMap.get(businessTypeVal));
	}
}
