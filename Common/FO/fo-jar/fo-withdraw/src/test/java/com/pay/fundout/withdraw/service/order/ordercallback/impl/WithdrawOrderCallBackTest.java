/** @Description 
 * @project 	poss-withdraw
 * @file 		WithdrawOrderCallBackTest.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Rick_lv			Create 
 */
package com.pay.fundout.withdraw.service.order.ordercallback.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;

@ContextConfiguration(locations = { 
		"classpath*:context/spring/base/*.xml", 
		"classpath*:context/spring/withdraw/context-fundout-withdraw-pefacade-service.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-accounting-service.xml", 
		"classpath*:context/spring/facade/context-fundout-appfacade-service.xml", 
		"classpath*:context/spring/ordercallback/context-fundout-orderhandler-service.xml",
		"classpath*:context/spring/ordercallback/context-fundout-ordercallback-service.xml",
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:context/spring/context-fundout-fee-cal-service.xml",
		"classpath*:context/spring/context-fundout-withdraworder-service.xml",
		"classpath*:context/spring/inf/context-fundout-notifyfacade-service.xml",
		"classpath*:context/spring/**/*.xml",
		"classpath*:config/spring/**/*.xml",
		"classpath*:context/*.xml" })
public class WithdrawOrderCallBackTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-withdraw-withDrawOrderCallBack")
	private OrderCallBackService callBack;
	@Resource(name = "fundout-withdraw-withDrawOrderSucc")
	private AccountingService accountingService;

	@Test
	public void testProcessOrder() {
		try {
			callBack.processOrderRdTx(buildParam(), accountingService);
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HandlerParam buildParam() {
		HandlerParam result = new HandlerParam();
		result.setBaseOrderDto(buildOrder());
		result.setOrderStatus(111);
		result.setWithdrawBusinessType(WithdrawBusinessType.WITHDRAW_ORDER_SUCC_PERSON.getBusinessType());
		return result;
	}

	private WithdrawOrderAppDTO buildOrder() {
		WithdrawOrderAppDTO order = new WithdrawOrderAppDTO();
		order.setSequenceId(System.currentTimeMillis());
		order.setOrderSeqId(""+System.currentTimeMillis());
		order.setAmount(1000L);
		order.setBankPurpose("代发gongzi");
		order.setUpdateTime(new Date());

		order.setMemberCode(10000000114L);
		order.setMemberAcc("20010200100011000000011410");
		order.setMemberAccType(10L);

		order.setStatus(101L);

		return order;
	}
}
