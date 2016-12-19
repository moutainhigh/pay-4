/**
 *  File: WithdrawOrderAppTest.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *
 */
package com.pay.poss.withdraw.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.jms.sender.JmsSender;
import com.pay.util.JSonUtil;

/**
 * 提现单元测试
 * 
 * @author Sandy_Yang
 */
@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/context-fundout-withdraworder-service.xml",
		"classpath*:config/spring/platform/*.xml" })
public class WithdrawOrderJMSTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private JmsSender jmsSender;
	
	@Autowired
	private WithdrawOrderService withdrawOrderService;

	@Test
	public void createWithdrawOrderTest() {
		String queueName = "withdraw.order.request";
		String jsonStr = JSonUtil.toJSonString("123456789");
		jmsSender.send(queueName, jsonStr);
	}
	
}
