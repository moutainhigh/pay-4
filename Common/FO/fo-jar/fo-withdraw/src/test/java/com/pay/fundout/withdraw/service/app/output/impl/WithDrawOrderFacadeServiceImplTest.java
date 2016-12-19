package com.pay.fundout.withdraw.service.app.output.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.bankrefund.BackfundOrder;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderFacadeService;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class WithDrawOrderFacadeServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-withdraw-withDrawOrderFacadeService")
	private WithDrawOrderFacadeService facadeService;

	@Test
	public void testQueryBackfundOrder() {
		BackfundOrder order = facadeService.queryBackfundOrder("121212", "nnn");
		Assert.assertNull(order.getBackfundOrderId());
		order = facadeService.queryBackfundOrder("2001012221839023717", "nnn");
		Assert.assertNotNull(order.getBackfundOrderId());
	}
}
