/**
 *  File: WithdrawOrderAuditServiceTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-11      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.poss.withdraw.jmsTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;

@ContextConfiguration(locations={"classpath*:config/spring/withdraw/*.xml","classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml" })
public class WithdrawOrderAuditServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	@Qualifier("wdOrdAuditService")
	WithdrawOrderAuditService wdOrdAuditService ;

	/**
	 * @param wdOrdAuditService the wdOrdAuditService to set
	 */
	public void setWdOrdAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}

	@Test
	public void testWDOrdQuery(){}






}
