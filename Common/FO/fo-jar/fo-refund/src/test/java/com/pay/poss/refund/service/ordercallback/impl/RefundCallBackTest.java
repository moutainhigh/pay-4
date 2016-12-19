 /** @Description 
 * @project 	poss-refund
 * @file 		RefundCallBackTest.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Rick_lv			Create 
*/
package com.pay.poss.refund.service.ordercallback.impl;


import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.refund.model.RefundOrderM;

@ContextConfiguration(locations = { 
		"classpath*:context/spring/base/*.xml", 
		"classpath*:context/spring/withdraw/context-fundout-withdraw-pefacade-service.xml", 
		"classpath*:context/spring/withdraw/context-fundout-withdraw-accounting-service.xml", 
		"classpath*:context/spring/ordercallback/context-fundout-orderhandler-service.xml",
		"classpath*:context/spring/ordercallback/context-fundout-refundordercallback-service.xml",
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/*.xml" })
public class RefundCallBackTest extends AbstractTestNGSpringContextTests{
	@Resource(name = "fundout-withdraw-chargeRefundCallBack")
	private OrderCallBackService callBack;
	@Resource(name = "fundout-withdraw-chargeRefundOrderSucc")
	private AccountingService accountingService;
	
//	@Test
	public void testNotifyHandlerParam() {
	}

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
		result.setWithdrawBusinessType(WithdrawBusinessType.PAYTOACCT_BATCHORDER_SUCC_PERSON.getBusinessType());
		return result;
	}

	private RefundOrderM buildOrder() {
		RefundOrderM refundOrderM = new RefundOrderM();
		refundOrderM.setDetailKy(System.currentTimeMillis());
		refundOrderM.setApplyAmount(new BigDecimal(1000L));
		// pay2AcctOrder.setParentOrder(111L);
		//refundOrderM.setRemarks("代发gongzi");
		refundOrderM.setApplyTime(new Date());

		refundOrderM.setMemberCode("10000000114");
		refundOrderM.setMemberAcc("20010200100011000000011410");
		refundOrderM.setMemberAccType(10);
		refundOrderM.setMemberName("宋江");
		refundOrderM.setApplyFrom("refund");

		refundOrderM.setStatus(101);

		return refundOrderM;
	}

}
