/**
 *  File: WithdrawOrderAuditServiceTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.accting;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.service.app.output.WithDrawOrderFacadeService;
import com.pay.fundout.withdraw.service.app.output.WithdrawOrderParam;

@ContextConfiguration(locations = {
		"classpath*:context/spring/ordercallback/*.xml",
		"classpath*:context/spring/ordercallback/context-fundout-ordercallback-service.xml",
		"classpath*:config/spring/remoting-bean.xml",
		"classpath*:config/spring/systemmanager/systemmanager-bean.xml",
		"classpath*:context/spring/withdrawrefund/context-fundout-withdraw-refund-bean.xml",
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/framework-context-db.xml",
		"classpath*:context/framework-context-dao.xml",
		"classpath*:context/framework-context-service.xml",
		"classpath*:context/spring/inf/*.xml",
		"classpath*:context/spring/flowprocess/*.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-accounting-service.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-pefacade-service.xml",
		"classpath*:context/spring/workflow/*.xml",
		"classpath*:context/spring/context-fundout-fee-cal-service.xml",
		"classpath*:context/spring/facade/context-fundout-appfacade-service.xml",
		"classpath*:context/spring/configbank/fundout-channel-configbank-service.xml",
		"classpath*:context/spring/facade/context-rm-facade-service.xml",
		"classpath*:context/spring/rmlimit/context-rm-limit-dao.xml",
		"classpath*:context/spring/appout/context-fundout-appout-service.xml",
		"classpath*:context/*.xml" })
		
		
public class WithdrawAcctingServiceTest extends
		AbstractTestNGSpringContextTests {
	
	@Resource(name="fundout-withdraw-withDrawOrderFacadeService")
	private WithDrawOrderFacadeService withDrawOrderFacadeService;
	
	@Test
	public void testAcctingReq() {
		WithdrawOrderParam withdrawOrder = new WithdrawOrderParam();
		withdrawOrder.setMemberCode(10000000114L);//memberCode
		withdrawOrder.setMemberType(10L);//默认个人用户
		withdrawOrder.setAccountName("testsets");//收款人姓名,实名姓名
		withdrawOrder.setBankAcct("111111111111111");//银行卡号
		withdrawOrder.setBankAcctType(2L);//默认借记卡0,存折1,信用卡2
		withdrawOrder.setMemberAcct("20010200100011000000011410");
		
		withdrawOrder.setMemberAccType(10L);
		
		withdrawOrder.setWithdrawType((short)2);
		
		withdrawOrder.setBusiType(2L);
		
		//转化金额到厘
		withdrawOrder.setAmount(1000L);
		
		withdrawOrder.setOrderRemarks("信用卡还款的测试");
		withdrawOrder.setBankKy("800");
		withdrawOrder.setBankBranch("这是分行名字");
		
		
		withdrawOrder.setStatus(8L);
		
		
		withDrawOrderFacadeService.saveWithdrawOrder(withdrawOrder);
	}
}
