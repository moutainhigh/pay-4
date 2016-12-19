package com.pay.poss.service.withdraw.orderafterproc.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterFailProcHandler;

@ContextConfiguration(locations = { "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class OrderAfterFailProcAlertHandlerTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-orderchandler-failProcHandler")
	private OrderAfterFailProcHandler failHandler;

	@Test
	public void testExecute() {
		failHandler.execute(buildOrder());
	}

	private OrderFailProcAlertModel buildOrder() {
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setAppFrom("test");
		result.setOrderSeq(111L);
		result.setOrderStatus(1);
		return result;
	}

}
