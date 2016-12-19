 /** @Description 
 * @project 	poss-withdraw
 * @file 		BatchSendAccountFacadeServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-11		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.accountmdp;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.service.accountmdp.BatchSendAccountFacadeService;

/**
 * <p>发送JMS消息 批量记账</p>
 * @author Henry.Zeng
 * @since 2010-10-11
 * @see 
 */
@ContextConfiguration(locations = {
		"classpath*:context/spring/withdrawschedule/context-fundout-withdrawmdp-service.xml",
		"classpath*:context/spring/appout/context-fundout-appout-service.xml",
		"classpath*:context/spring/inf/context-fundout-notifyfacade-service.xml",
		"classpath*:context/notification-client-jms.xml"
})
public class BatchSendAccountFacadeServiceTest extends AbstractTestNGSpringContextTests  {
	
	@Autowired
	@Qualifier("fundout_withdraw_batchSendAccountFacadeService")
	private BatchSendAccountFacadeService accountFacadeService;

	@Test
	public void sendMq2BatchAccount() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("workOrderId", "2001010090028000646");
		params.put("orderId", "2001010091829000710");
		params.put("issucc", "1");
		accountFacadeService.sendMq2BatchAccount(params);
		
//		params = new HashMap<String, String>();
//		params.put("workOrderId", "2001010112021000931");
//		params.put("orderId", "2001010112021000930");
//		params.put("issucc", "1");
//		accountFacadeService.sendMq2BatchAccount(params);
//
//		params = new HashMap<String, String>();
//		params.put("workOrderId", "2001010072157000518");
//		params.put("orderId", "2001010090938000669");
//		params.put("issucc", "1");
//		accountFacadeService.sendMq2BatchAccount(params);
	}
	
}
