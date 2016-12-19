/**
 *  File: HandlerParam.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.orderhandlermanage;

import java.io.Serializable;

import com.pay.pe.dto.AccountingDto;

/**
 * 订单处理参数对象
 * @author zliner
 *
 */
public class HandlerParam implements Serializable {
	//处理对象
	private static final long serialVersionUID = 843308906921780850L;
	//出款业务订单类型
	private String withdrawBusinessType;
	//出款订单状态
	private Integer orderStatus;
	//通用订单对象
	private BaseOrderDTO baseOrderDto;
	//通用记账对象
	private AccountingDto accountingDto;
	
	public String getWithdrawBusinessType() {
		return withdrawBusinessType;
	}
	public void setWithdrawBusinessType(String withdrawBusinessType) {
		this.withdrawBusinessType = withdrawBusinessType;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BaseOrderDTO getBaseOrderDto() {
		return baseOrderDto;
	}
	public void setBaseOrderDto(BaseOrderDTO baseOrderDto) {
		this.baseOrderDto = baseOrderDto;
	}
	public AccountingDto getAccountingDto() {
		return accountingDto;
	}
	public void setAccountingDto(AccountingDto accountingDto) {
		this.accountingDto = accountingDto;
	}
}
