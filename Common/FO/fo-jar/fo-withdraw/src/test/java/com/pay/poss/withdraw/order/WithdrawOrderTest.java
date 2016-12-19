/**
 *  File: WithdrawOrderAppTest.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *
 */
package com.pay.poss.withdraw.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppParaDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.dao.Page;

/**
 * 提现单元测试
 * 
 * @author Sandy_Yang
 */
@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/context-fundout-withdraworder-service.xml",
		"classpath*:config/spring/platform/*.xml",
		"classpath*:context/spring/**/*.xml",
		})
public class WithdrawOrderTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WithdrawOrderService withdrawOrderService;

	@Test
	public void queryWithdrawOrderTest() {
		WithdrawOrderAppParaDTO withdrawOrderAppParaDTO = new WithdrawOrderAppParaDTO();
		withdrawOrderAppParaDTO.setMemberCode(123456785L);
		withdrawOrderAppParaDTO.setStatus(2L);
		Page<WithdrawOrderAppDTO> page = withdrawOrderService
				.queryWithdrawOrder(withdrawOrderAppParaDTO, 1,9);
		List<WithdrawOrderAppDTO> list = page.getResult();
		for (WithdrawOrderAppDTO withdrawOrderAppDTO : list) {
			System.err.println(withdrawOrderAppDTO.getAccountName());
		}
	}
	
//	@Test
	public void getByIdTest() {
		WithdrawOrderAppDTO s = withdrawOrderService.getWithdrawOrder(2001009291142000009L);
		System.out.println(s.getAccountName());
	}
	
//	@Test
	public void dayTimesTotalTest() {
		int s = withdrawOrderService.dayTimesTotal(123456785L);
		System.err.println(s);
	}
	
//	@Test
	public void dayMoneyTotalTest() {
		Long s = withdrawOrderService.dayMoneyTotal(123456785L);
		System.err.println(s);
	}
	
//	@Test
	public void monthMoneyTotalTest() {
		Long s = withdrawOrderService.monthMoneyTotal(123456785L);
		System.err.println(s);
	}
	
	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
}
