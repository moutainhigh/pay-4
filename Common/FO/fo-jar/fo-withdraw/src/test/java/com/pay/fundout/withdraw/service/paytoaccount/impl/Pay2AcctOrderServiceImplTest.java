package com.pay.fundout.withdraw.service.paytoaccount.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctOrderService;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class Pay2AcctOrderServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-withdraw-pay2AcctOrderService")
	private Pay2AcctOrderService orderService;

	@Test
	public void testCreateOrderRnTx() {
		Pay2AcctOrder order = buildOrder(Pay2AcctService.REQUEST_CODE_FOR_FO);
		orderService.createOrderRnTx(order, Pay2AcctService.REQUEST_CODE_FOR_FO);
	}

	private Pay2AcctOrder buildOrder(String request) {
		Pay2AcctOrder pay2AcctOrder = new Pay2AcctOrder();
		pay2AcctOrder.setSequenceId(System.currentTimeMillis());
		pay2AcctOrder.setAmount(1000L);
		pay2AcctOrder.setParentOrder(111L);
		pay2AcctOrder.setRemarks("代发gongzi");
		pay2AcctOrder.setUpdateDate(new Date());
		pay2AcctOrder.setRequestFrom(request);

		pay2AcctOrder.setPayerMember(10000000114L);
		pay2AcctOrder.setPayerAcctCode("20010200100011000000011410");
		pay2AcctOrder.setPayerAcctType(10);
		pay2AcctOrder.setPayeeMember(10000000117L);
		pay2AcctOrder.setPayeeAcctCode("20010200100011000000011710");
		pay2AcctOrder.setPayeeAcctType(10);

		return pay2AcctOrder;
	}

}
